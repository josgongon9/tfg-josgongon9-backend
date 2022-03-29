package com.josgongon9.tfgwebbackend.service.impl;

import com.josgongon9.tfgwebbackend.model.Organization;
import com.josgongon9.tfgwebbackend.repository.OrganizationRepository;
import com.josgongon9.tfgwebbackend.repository.VacationRepository;
import com.josgongon9.tfgwebbackend.service.IOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationServiceImpl implements IOrganizationService {

    @Autowired
    OrganizationRepository organizationRepository;

    @Override
    public Organization createOrganization(Organization organization) {
        Organization organizationRes = organizationRepository.save(new Organization(organization.getName(),organization.getDescription(),organization.getPhoneNumber(),organization.getUrl()));
        return organizationRes;
    }
}
