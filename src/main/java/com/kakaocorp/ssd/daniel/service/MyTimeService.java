package com.kakaocorp.ssd.daniel.service;

import com.kakaocorp.ssd.daniel.common.AgitConstants;
import com.kakaocorp.ssd.daniel.model.Assignee;
import com.kakaocorp.ssd.daniel.model.ResultOfMytimeApiRequestModel;
import com.kakaocorp.ssd.daniel.model.User;
import com.kakaocorp.ssd.daniel.model.WorkingTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

@Slf4j
@Service
public class MyTimeService {

    private String myTimeApiUrl;
    private String myTimeApiKey;

    private RestTemplate restTemplate;
    private HttpEntity<?> httpEntity;

    public MyTimeService(@Value("${mytime.apiurl}") String myTimeApiUrl, @Value("${mytime.apikey}") String myTimeApiKey) {
        this.myTimeApiUrl = myTimeApiUrl;
        this.myTimeApiKey = myTimeApiKey;
        this.restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION, AgitConstants.MYTIME_KAKAOAK + myTimeApiKey);
        this.httpEntity = new HttpEntity<>(httpHeaders);
    }

    public List<User> getAbsenceUserList(List<Assignee> assigneeList) {
        if (CollectionUtils.isEmpty(assigneeList)) {
            return null;
        }
        String encodeType = "";
        String agitIdList = "";
        try {
            encodeType = URLEncoder.encode(",", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage());
        }

        agitIdList = assigneeList.stream().map(Assignee::getAgitId).collect(joining(encodeType));

        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(myTimeApiUrl).queryParam("userIds", agitIdList).build();

        ResultOfMytimeApiRequestModel resultOfMytimeApiRequestModel = restTemplate
                .exchange(uriComponents.toUriString(), HttpMethod.GET, httpEntity, ResultOfMytimeApiRequestModel.class).getBody();

        List<User> absenceUserList = resultOfMytimeApiRequestModel.getUserList().stream().filter(MyTimeService::isNotWorking).collect(Collectors.toList());

        return absenceUserList;
    }

    public static boolean isNotWorking(User user) {
        return isNotWorking(user, LocalTime.now());
    }

    public static boolean isNotWorking(User user, LocalTime currentTime) {
        for (WorkingTime workingTime : user.getWorkingTimeList()) {

            LocalTime startTime = LocalTime.parse(workingTime.getStartTime());
            LocalTime endTime = LocalTime.parse(workingTime.getEndTime());

            if ((startTime.isBefore(currentTime) && currentTime.isBefore(endTime))
                    || startTime.compareTo(currentTime) == 0) {
                return false;

            }
        }
        return true;
    }
}
