package com.josgongon9.tfgwebbackend.service;

import com.josgongon9.tfgwebbackend.exception.MyOwnException;
import com.josgongon9.tfgwebbackend.model.Organization;
import com.josgongon9.tfgwebbackend.model.response.OrganizationResponse;

import java.util.List;

public interface IOrganizationService {

    Organization createOrganization(OrganizationResponse organization) throws Exception;

    Organization updateOrganization(OrganizationResponse organization) throws Exception;

    void deleteOrganization(String id) throws Exception;

    Organization updateUsers (String id, String idUser) throws Exception;

    Organization getOrganizationByUser(String idUser) throws MyOwnException;

    void updateMods(String id, String idUser) throws Exception;;

    List<Organization> getAll();
}
