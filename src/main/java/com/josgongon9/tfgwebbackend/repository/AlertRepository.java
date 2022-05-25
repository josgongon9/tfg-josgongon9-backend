package com.josgongon9.tfgwebbackend.repository;

import com.josgongon9.tfgwebbackend.model.Alert;
import com.josgongon9.tfgwebbackend.model.Vacation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertRepository extends MongoRepository<Alert, String> {

    List<Alert> findAllByNameLike(String name);
}