package com.josgongon9.tfgwebbackend.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.josgongon9.tfgwebbackend.model.ERole;
import com.josgongon9.tfgwebbackend.model.Role;



public interface RoleRepository extends MongoRepository<Role, String> {
  Optional<Role> findByName(ERole name);
}
