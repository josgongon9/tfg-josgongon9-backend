package com.josgongon9.tfgwebbackend.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;

import javax.annotation.PostConstruct;

@Configuration
public class AccesorMessage {

    @Autowired
    private MessageSource messageSource;

    private MessageSourceAccessor accesor;

    @PostConstruct
    private void init() {
        accesor = new MessageSourceAccessor(messageSource);
    }

    public String get(String code, String... params) {
        return accesor.getMessage(code, params);
    }
}
