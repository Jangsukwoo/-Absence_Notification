package com.kakaocorp.ssd.daniel.service;

import com.kakaocorp.ssd.daniel.dao.WaitNotificationDao;
import com.kakaocorp.ssd.daniel.dto.EventRequestDto;
import com.kakaocorp.ssd.daniel.dto.WaitNotificationDto;
import com.kakaocorp.ssd.daniel.model.CommentOfRequestModel;
import com.kakaocorp.ssd.daniel.model.User;
import com.kakaocorp.ssd.daniel.util.WebHookClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static java.util.stream.Collectors.joining;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebHookService {

    @Value("${webhook.apiurl}")
    private String webHookApiUrl;
    @Value("${webhook.apikey}")
    private String webHookApiKey;

    private final WebHookClient webHookClient;

    private final WaitNotificationDao waitNotificationDao;

    public void postComment(List<User> absenceUserList, EventRequestDto eventRequestDto, String reporterAgitId) {
        if (CollectionUtils.isEmpty(absenceUserList)) {
            return;
        }
        CommentOfRequestModel commentOfRequestModel = new CommentOfRequestModel();

        commentOfRequestModel.setParentId(eventRequestDto.getRootPostId());
        String notificationText = setAbsenceMessage(reporterAgitId, absenceUserList);
        commentOfRequestModel.setText(notificationText);

        String url = webHookApiUrl + webHookApiKey;
        webHookClient.postRequest(url, commentOfRequestModel);

        for (User user : absenceUserList) {
            waitNotificationDao.insertNotification(new WaitNotificationDto(eventRequestDto.getRootPostId(), user.getUserId()));
        }
    }

    private String setAbsenceMessage(String reporterAgitId, List<User> absenceUserList) {
        String notification = "@" + reporterAgitId + "\n";
        notification += absenceUserList.stream().map(User::getUserId).collect(joining(", "));
        notification += "는 현재 부재중 입니다. 담당자가 돌아오면 제가 한번 더 알리겠습니다!";
        return notification;
    }
}