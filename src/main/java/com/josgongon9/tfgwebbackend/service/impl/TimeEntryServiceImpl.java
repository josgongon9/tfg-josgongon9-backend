package com.josgongon9.tfgwebbackend.service.impl;

import com.josgongon9.tfgwebbackend.exception.EntityNotFoundException;
import com.josgongon9.tfgwebbackend.exception.MyOwnException;
import com.josgongon9.tfgwebbackend.model.TimeEntry;
import com.josgongon9.tfgwebbackend.model.User;
import com.josgongon9.tfgwebbackend.repository.TimeEntryRepository;
import com.josgongon9.tfgwebbackend.service.ITimeEntryService;
import org.apache.commons.lang3.time.DateUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    @Override
    public LocalTime getNowTimeEntry(String idUser) {
        LocalTime res = null;
        User user = userRepository.findById(idUser).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        List<TimeEntry> allTimeEntryUser = user.getTimeEntries();
        if (!allTimeEntryUser.isEmpty()) {
            for (TimeEntry t : allTimeEntryUser) {
                Date now = new Date();
                boolean samedate = DateUtils.isSameDay(t.getDate(), now);
                if (samedate) {
                    LocalTime sum = LocalTime.parse(t.getTotalTime(), DateTimeFormatter.ofPattern("H:m"));
                    if (!Objects.isNull(res)) {
                        res = res.plusHours(sum.getHour())
                                .plusMinutes(sum.getMinute());
                    } else {
                        res = sum;
                    }
                }
            }
        }
        return res;
    }

    @Override
    public Date getLastTimeEntry(String idUser) throws MyOwnException {
        Date res = null;
        User user = userRepository.findById(idUser).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        List<TimeEntry> allTimeEntryUser = user.getTimeEntries();
        if (!allTimeEntryUser.isEmpty()) {
            res = allTimeEntryUser.stream().sorted(Comparator.comparing(TimeEntry::getDate).reversed()).findFirst().orElseThrow(() -> new MyOwnException("No hay entradas de tiempo para mostrar")).getDate();
        }
        return res;
    }
}


