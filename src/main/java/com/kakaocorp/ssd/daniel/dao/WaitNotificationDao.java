package com.kakaocorp.ssd.daniel.dao;

import com.kakaocorp.ssd.daniel.dto.WaitNotificationDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface WaitNotificationDao {
    int insertNotification(WaitNotificationDto waitNotificationDto);

    List<WaitNotificationDto> findAll();
}
