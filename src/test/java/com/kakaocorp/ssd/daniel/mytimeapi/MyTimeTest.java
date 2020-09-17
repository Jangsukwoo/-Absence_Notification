package com.kakaocorp.ssd.daniel.mytimeapi;

import com.kakaocorp.ssd.daniel.model.User;
import com.kakaocorp.ssd.daniel.model.WorkingTime;
import com.kakaocorp.ssd.daniel.service.MyTimeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MyTimeService.class)
public class MyTimeTest {

    @Autowired
    private MyTimeService myTimeService;

    @DisplayName("근무 시간이 주어질 때 호출 시간으로 부재중 확인하는 테스트")
    @Test
    public void absenceTest() {
        User user = new User();
        List<WorkingTime> workingTimeList = new ArrayList<>();
        WorkingTime workingTime = new WorkingTime();
        workingTime.setStartTime("10:00");
        workingTime.setEndTime("12:00");
        workingTimeList.add(workingTime);
        workingTime = new WorkingTime();
        workingTime.setStartTime("13:00");
        workingTime.setEndTime("19:00");
        workingTimeList.add(workingTime);

        //근무 OFF = true
        user.setWorkingTimeList(workingTimeList);
        LocalTime testTime = LocalTime.parse("09:00");
        assertTrue("기대값 True와 다릅니다.", myTimeService.isNotWorking(user, testTime));
        testTime = LocalTime.parse("12:30");
        assertTrue("기대값 True와 다릅니다.", myTimeService.isNotWorking(user, testTime));
        testTime = LocalTime.parse("19:30");
        assertTrue("기대값 True와 다릅니다.", myTimeService.isNotWorking(user, testTime));
        testTime = LocalTime.parse("12:00");
        assertTrue("기대값 True와 다릅니다.", myTimeService.isNotWorking(user, testTime));

        //근무 ON = false
        testTime = LocalTime.parse("10:00");
        assertFalse("기대값 False 다릅니다.", myTimeService.isNotWorking(user, testTime));
        testTime = LocalTime.parse("13:00");
        assertFalse("기대값 False 다릅니다.", myTimeService.isNotWorking(user, testTime));
    }
}
