package com.josgongon9.tfgwebbackend.service;

import com.josgongon9.tfgwebbackend.model.TimeEntry;

import java.util.List;

public interface ITimeEntryService {
     List<TimeEntry> getAllByUserTimeEntries();

     TimeEntry findById(String id);

     TimeEntry createTimeEntry(TimeEntry timeEntry);

     void deleteTimeEntry(String id);
}
