package com.josgongon9.tfgwebbackend.service.impl;

import com.josgongon9.tfgwebbackend.exception.EntityNotFoundException;
import com.josgongon9.tfgwebbackend.model.TimeEntry;
import com.josgongon9.tfgwebbackend.model.User;
import com.josgongon9.tfgwebbackend.repository.TimeEntryRepository;
import com.josgongon9.tfgwebbackend.service.ITimeEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TimeEntryServiceImpl extends BasicServiceImpl implements ITimeEntryService {

    @Autowired
    private TimeEntryRepository timeEntryRepository;

    public List<TimeEntry> getAllByUserTimeEntries() {
        List<TimeEntry> res = new ArrayList<>();

        res = this.getUser().getTimeEntries();

        return res;
    }

    public TimeEntry findById(String id) {
        return timeEntryRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public TimeEntry createTimeEntry(TimeEntry timeEntry) {
        TimeEntry _timeEntry = timeEntryRepository.save(new TimeEntry(timeEntry.getDate(), timeEntry.getComment(), timeEntry.getStartTime(), timeEntry.getEndTime(), timeEntry.getTotalTime()));

        User user = userRepository.findByUsername(this.getUser().getUsername()).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        user.getTimeEntries().add(_timeEntry);
        userRepository.save(user);
        return _timeEntry;
    }

    public void deleteTimeEntry(String id) {
        ObjectId idTime = new ObjectId(id);
        List<User> userList = userRepository.findUserByVacation(idTime);
        for (User user : userList) {
            user.getTimeEntries().removeIf(x -> x.getId().equals(id));
            userRepository.save(user);
        }

        timeEntryRepository.deleteById(id);

    }

}
