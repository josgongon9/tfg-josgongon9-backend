package com.josgongon9.tfgwebbackend.repository;

import com.josgongon9.tfgwebbackend.model.Organization;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrganizationRepository extends MongoRepository<Organization, String> {

}