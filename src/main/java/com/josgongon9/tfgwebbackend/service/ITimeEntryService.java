package com.josgongon9.tfgwebbackend.service;

import com.josgongon9.tfgwebbackend.exception.MyOwnException;
import com.josgongon9.tfgwebbackend.model.TimeEntry;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public interface ITimeEntryService {
     List<TimeEntry> getAllByUserTimeEntries();

     TimeEntry findById(String id);

     TimeEntry createTimeEntry(TimeEntry timeEntry);

     void deleteTimeEntry(String id);

     LocalTime getNowTimeEntry(String idUser);

     Date getLastTimeEntry(String idUser) throws MyOwnException;

     List<TimeEntry> getAllTimeEntrysByUser(String idUser);
}
