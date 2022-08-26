package com.josgongon9.tfgwebbackend.service;

import com.josgongon9.tfgwebbackend.exception.MyOwnException;

public interface IUserService {
    String generateDataToExport(String id) throws MyOwnException;
}
