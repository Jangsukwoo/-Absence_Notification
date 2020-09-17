package com.kakaocorp.ssd.daniel.service;

import com.kakaocorp.ssd.daniel.dto.EventRequestDto;
import com.kakaocorp.ssd.daniel.model.Assignee;
import com.kakaocorp.ssd.daniel.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    /*
1. 아지트 Api를 활용하여 글 번호로 글 내용 조회 및 호출자 추출
2. mytimeAPI로 부재중인지 확인.
3. 부재중인 근무자 있는 경우 해당 근무자에 대한 off 알림 댓글 달기
4. 부재중인 근무자가 없는 경우 아무 액션없음
5. 부재중인 근무자에 대해 데이터베이스에 저장해뒀다가 부재중에 요청이 왔었음을 알림 (다음 근무 시작시점, 스케줄링)
6. 스케줄링은 어떻게할지?
7. 민감 데이터 노출에 대한 고민은 천천히.
8. 테스트코드를 만들면서 할것인가
9. 필터, 로거?
 */
    @Autowired
    private MyTimeService myTimeService;
    @Autowired
    private AgitService agitService;
    @Autowired
    private WebHookService webHookService;
    //    @Autowired
    //    private WaitNotificationDao waitNotificationDao;

    public void eventHandler(EventRequestDto eventCallBackRequestDto) {
        List<Assignee> mentionedIdList = agitService.getAssigneeList(eventCallBackRequestDto.getEvent().getId());
        String reporterAgitId = agitService.getReporterAgitId(eventCallBackRequestDto.getEvent().getUserId());
        List<User> absenceUserList = myTimeService.getAbsenceUserList(mentionedIdList);
        webHookService.postComment(absenceUserList, eventCallBackRequestDto, reporterAgitId);
    }
}
