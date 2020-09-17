package com.kakaocorp.ssd.daniel.mybatisTest;

import com.kakaocorp.ssd.daniel.dao.WaitNotificationDao;
import com.kakaocorp.ssd.daniel.dto.WaitNotificationDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertNotNull;

@Slf4j
@SpringBootTest
public class MybatisTest {
    @Autowired
    private WaitNotificationDao waitNotificationDao;

    @DisplayName("waitnotification 테이블에 있는 모든 데이터를 가져오는 테스트")
    @Test
    public void findAllTest() {
        List<WaitNotificationDto> waitNotificationDtoList = waitNotificationDao.findAll();
        assertNotNull("DB에 데이터가 있기 때문에 존재해야합니다.", waitNotificationDtoList);
    }
}
