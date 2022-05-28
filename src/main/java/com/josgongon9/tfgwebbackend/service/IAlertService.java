package com.josgongon9.tfgwebbackend.service;

import com.josgongon9.tfgwebbackend.exception.MyOwnException;
import com.josgongon9.tfgwebbackend.model.Alert;

import java.util.List;

public interface IAlertService {
    List<Alert> getAllByOrg(String idOrg) throws MyOwnException;

    Alert createAlert(Alert alert, String idOrg) throws MyOwnException;

    void deleteAlert(String id);

    Alert updateStateAlert(Alert alert, String id) throws MyOwnException;
}
