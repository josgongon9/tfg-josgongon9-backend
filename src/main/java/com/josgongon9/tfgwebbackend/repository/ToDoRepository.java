package com.josgongon9.tfgwebbackend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.josgongon9.tfgwebbackend.model.ToDo;

public interface ToDoRepository extends MongoRepository<ToDo, String> {
}
