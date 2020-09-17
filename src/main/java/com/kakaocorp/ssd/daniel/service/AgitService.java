package com.kakaocorp.ssd.daniel.service;

import com.kakaocorp.ssd.daniel.common.AgitConstants;
import com.kakaocorp.ssd.daniel.model.Assignee;
import com.kakaocorp.ssd.daniel.model.PostContentOfAgitRequestModel;
import com.kakaocorp.ssd.daniel.model.Reporter;
import com.kakaocorp.ssd.daniel.model.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
public class AgitService {
    private String postUrl;
    private String usersUrl;
    private String botToken;
    private RestTemplate restTemplate;
    private HttpEntity<?> httpEntity;

    public AgitService(@Value("${agit.posturl}") String postUrl, @Value("${agit.usersurl}") String usersUrl, @Value("${agit.bottoken}") String botToken) {
        this.postUrl = postUrl;
        this.usersUrl = usersUrl;
        this.botToken = botToken;
        this.restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION, AgitConstants.AGIT_API_BEARER + this.botToken);
        this.httpEntity = new HttpEntity<>(httpHeaders);
    }

    public List<Assignee> getAssigneeList(String postId) {

        PostContentOfAgitRequestModel postContentOfAgitRequestModel = restTemplate.exchange(postUrl + postId, HttpMethod.GET, httpEntity, PostContentOfAgitRequestModel.class)
                .getBody();
        List<Task> taskList = postContentOfAgitRequestModel.getTaskList();
        if (!CollectionUtils.isEmpty(taskList)) {
            List<Assignee> assigneeList = taskList.get(0).getAssigneeList();
            return assigneeList;
        }
        return null;
    }

    public String getReporterAgitId(String userId) {
        Reporter reporter = restTemplate.exchange(usersUrl + userId, HttpMethod.GET, httpEntity, Reporter.class)
                .getBody();
        return reporter.getUser().getAgitId();
    }
}
