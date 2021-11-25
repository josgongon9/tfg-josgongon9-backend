package com.josgongon9.tfgwebbackend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.josgongon9.tfgwebbackend.model.TimeEntry;

public interface TimeEntryRepository extends MongoRepository<TimeEntry, String> {
	
}
