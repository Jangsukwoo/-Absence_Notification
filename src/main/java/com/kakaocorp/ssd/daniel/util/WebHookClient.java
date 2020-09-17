package com.kakaocorp.ssd.daniel.util;

import com.kakaocorp.ssd.daniel.model.CommentOfRequestModel;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Component
public class WebHookClient {

    private HttpEntity<?> httpEntity;
    private RestTemplate restTemplate;
    private HttpHeaders httpHeaders;

    @PostConstruct
    public void init() {
        httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, "Application/json");
        restTemplate = new RestTemplateBuilder().build();
    }

    public void postRequest(String url, CommentOfRequestModel commentOfRequestModel) {
        this.httpEntity = new HttpEntity<>(commentOfRequestModel, httpHeaders);
        this.restTemplate.postForLocation(url, httpEntity, CommentOfRequestModel.class);
    }
}
