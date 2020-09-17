package com.kakaocorp.ssd.daniel.controller;

import com.kakaocorp.ssd.daniel.common.AgitConstants;
import com.kakaocorp.ssd.daniel.dao.WaitNotificationDao;
import com.kakaocorp.ssd.daniel.dto.EventRequestDto;
import com.kakaocorp.ssd.daniel.dto.UrlVerificationResponseDto;
import com.kakaocorp.ssd.daniel.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AgitEventController {
    @Autowired
    private EventService eventService;

    @Autowired
    private WaitNotificationDao waitNotificationDao;

    @PostMapping("/publishEvent")
    public ResponseEntity publishEvent(@RequestBody EventRequestDto eventRequestDto) {

        String eventType = eventRequestDto.getType();
        UrlVerificationResponseDto urlVerificationResponseDto = new UrlVerificationResponseDto();

        switch (eventType) {
        case AgitConstants.EVENT_TYPE_URL_VERIFICATION:
            urlVerificationResponseDto.setChallenge(eventRequestDto.getChallenge());
            break;
        case AgitConstants.EVENT_TYPE_EVENT_CALLBACK:
            eventService.eventHandler(eventRequestDto);
            break;
        default:
            log.error("Invalid Request Type! type:{}", eventType);
            break;
        }
        return ResponseEntity.status(HttpStatus.OK).body(urlVerificationResponseDto);
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public void exceptionHandler(DataIntegrityViolationException e) {
        log.error(e.getMessage(), e);
        if (e instanceof DuplicateKeyException) {
            //TODO Exception Handling. 추후 핸들링이 필요한 경우 Handling Logic과 Dao 관련 예외처리 방식 고민
        }
    }
}