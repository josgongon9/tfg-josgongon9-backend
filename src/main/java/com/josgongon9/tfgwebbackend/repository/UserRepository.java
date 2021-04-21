package com.josgongon9.tfgwebbackend.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.josgongon9.tfgwebbackend.model.User;


public interface UserRepository extends MongoRepository<User, String> {
  Optional<User> findByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);
}
