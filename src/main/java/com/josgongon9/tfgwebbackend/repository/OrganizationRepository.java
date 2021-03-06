package com.josgongon9.tfgwebbackend.repository;

import com.josgongon9.tfgwebbackend.model.Organization;
import com.josgongon9.tfgwebbackend.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface OrganizationRepository extends MongoRepository<Organization, String> {

    Optional<Organization> findFirstByName(String name);

    List<Organization> findAllByNameLike(String name);

    @Query("{'alerts' :{'$ref' : 'alerts' , '$id' : ?0}}")
    Organization findOrganizationByAlert(ObjectId id);

    Organization findOrganizationByUsuarios(ObjectId id);

}