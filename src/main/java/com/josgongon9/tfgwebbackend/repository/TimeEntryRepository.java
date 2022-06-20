package com.josgongon9.tfgwebbackend.repository;

import com.josgongon9.tfgwebbackend.model.Organization;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.josgongon9.tfgwebbackend.model.TimeEntry;
import org.springframework.data.mongodb.repository.Query;

public interface TimeEntryRepository extends MongoRepository<TimeEntry, String> {




}
