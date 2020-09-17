package com.kakaocorp.ssd.daniel.agitTest;

import com.kakaocorp.ssd.daniel.model.Assignee;
import com.kakaocorp.ssd.daniel.service.AgitService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertNotNull;
import static org.springframework.test.util.AssertionErrors.assertNull;

@SpringBootTest
public class AgitTest {
    @Autowired
    private AgitService agitService;

    @DisplayName("요청 포스트에 멘션된 유저들을 잘 가져오는지 테스트를 한다.")
    @Test
    public void requestAssigneeTest() {

        String testPostId;
        List<Assignee> assigneeList;

        //글에 요청 멘션이 있을 때
        testPostId = "330558927";
        assigneeList = agitService.getAssigneeList(testPostId);
        assertNotNull("멘션이 있기 때문에 Null이면 안됩니다.", assigneeList);

        //글에 요청 멘션이 없을 때
        testPostId = "330563214";
        assigneeList = agitService.getAssigneeList(testPostId);
        assertNull("멘션이 없기 대문에 NotNull이면 안됩니다.", assigneeList);
    }

    @DisplayName("리포터 이름 가져오는 테스트")
    @Test
    public void requestAgitUser() {
        String id = "300039313";
        String agitId = agitService.getReporterAgitId(id);
        assertNotNull("유저번호가 있다면 유저 닉네임도 있어야 합니다.", agitId);
    }
}
