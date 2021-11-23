package com.josgongon9.tfgwebbackend.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.josgongon9.tfgwebbackend.model.Tutorial;


public interface TutorialRepository extends MongoRepository<Tutorial, String> {
  List<Tutorial> findByPublished(boolean published);
  List<Tutorial> findByTitleContaining(String title);
}