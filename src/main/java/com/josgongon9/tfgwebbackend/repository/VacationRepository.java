package com.josgongon9.tfgwebbackend.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.josgongon9.tfgwebbackend.model.Vacation;


public interface VacationRepository extends MongoRepository<Vacation, String> {
  List<Vacation> findByPublished(boolean published);
  List<Vacation> findByTitleContaining(String title);
}