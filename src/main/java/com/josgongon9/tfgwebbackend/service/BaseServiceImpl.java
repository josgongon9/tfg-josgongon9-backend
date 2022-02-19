package com.josgongon9.tfgwebbackend.service;

import com.josgongon9.tfgwebbackend.service.impl.IBaseService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;

public class BaseServiceImpl implements IBaseService {

    private String user = null;


    public String getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getPrincipal().toString();
    }

    public Date getFechaActual() {
        return new Date();
    }
}
