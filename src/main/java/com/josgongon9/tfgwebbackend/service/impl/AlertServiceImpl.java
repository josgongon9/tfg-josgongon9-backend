package com.josgongon9.tfgwebbackend.service.impl;

import com.josgongon9.tfgwebbackend.exception.MyOwnException;
import com.josgongon9.tfgwebbackend.model.Alert;
import com.josgongon9.tfgwebbackend.model.Organization;
import com.josgongon9.tfgwebbackend.repository.AlertRepository;
import com.josgongon9.tfgwebbackend.repository.OrganizationRepository;
import com.josgongon9.tfgwebbackend.service.IAlertService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class AlertServiceImpl extends BasicServiceImpl implements IAlertService {

    @Autowired
    OrganizationRepository organizationRepository;

    @Autowired
    AlertRepository alertRepository;

    @Override
    public List<Alert> getAllByOrg(String idOrg) throws MyOwnException {
        List<Alert> res = new ArrayList<>();
        Organization organization = organizationRepository.findById(idOrg).orElseThrow(() -> new MyOwnException("Organizacion no encontrada"));
        res = organization.getAlerts();
        return res;
    }

    @Override
    public Alert createAlert(Alert alert, String idOrg) throws MyOwnException {
        Alert alertRes = alertRepository.save(new Alert(alert.getId(), alert.getName(), alert.getDescription(), false, new Date()));
        Organization organization = organizationRepository.findById(idOrg).orElseThrow(() -> new MyOwnException("Organizacion no encontrada"));
        organization.getAlerts().add(alertRes);
        organizationRepository.save(organization);
        return alertRes;
    }

    @Override
    public void deleteAlert(String id) {
        ObjectId idAlert = new ObjectId(id);
        Organization organization = organizationRepository.findOrganizationByAlert(idAlert);
        if (!Objects.isNull(organization)) {
            organization.getAlerts().removeIf(x -> x.getId().equals(id));
            organizationRepository.save(organization);
        }

        alertRepository.deleteById(id);
    }

    @Override
    public Alert updateStateAlert(Alert alert, String id) throws MyOwnException {
        Alert alertRes = alertRepository.findById(id).orElseThrow(() -> new MyOwnException("Alerta no encontrada"));
        alertRes.setShow(alert.getShow());
        alertRepository.save(alertRes);
        return alertRes;
    }
}
