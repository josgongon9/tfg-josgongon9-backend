package com.josgongon9.tfgwebbackend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.josgongon9.tfgwebbackend.exception.EntityNotFoundException;
import com.josgongon9.tfgwebbackend.model.TimeEntry;
import com.josgongon9.tfgwebbackend.repository.TimeEntryRepository;

@Service
public class TimeEntryServiceImpl {
	@Autowired
	private TimeEntryRepository timeEntryRepository;

	public List<TimeEntry> findAll() {
		return timeEntryRepository.findAll();
	}

	public TimeEntry findById(String id) {
		return timeEntryRepository.findById(id).orElseThrow(EntityNotFoundException::new);
	}

	public TimeEntry save(TimeEntry timeEntry) {
		return timeEntryRepository.save(timeEntry);
	}

	public void deleteById(String id) {
		timeEntryRepository.deleteById(id);
	}

}
