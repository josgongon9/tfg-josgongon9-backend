package com.josgongon9.tfgwebbackend.service;

import com.josgongon9.tfgwebbackend.model.Organization;
import com.josgongon9.tfgwebbackend.model.response.OrganizationResponse;

public interface IOrganizationService {

    Organization createOrganization(OrganizationResponse organization) throws Exception;

    Organization updateOrganization(OrganizationResponse organization) throws Exception;

    void deleteOrganization(String id) throws Exception;

    Organization updateUsers (String id, String idUser) throws Exception;

}
