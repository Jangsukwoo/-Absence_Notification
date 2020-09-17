package com.kakaocorp.ssd.daniel.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class DatabaseConfiguration {
    @Autowired
    private ApplicationContext applicationContext;

}