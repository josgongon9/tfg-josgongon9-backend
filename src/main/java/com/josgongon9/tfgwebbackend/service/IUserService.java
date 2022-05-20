package com.josgongon9.tfgwebbackend.service;

import com.josgongon9.tfgwebbackend.exception.MyOwnException;
import com.josgongon9.tfgwebbackend.model.User;
import org.bson.json.JsonObject;

public interface IUserService {
    String generateDataToExport(String id) throws MyOwnException;
}
