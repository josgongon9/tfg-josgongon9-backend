package com.josgongon9.tfgwebbackend.service.impl;

import com.josgongon9.tfgwebbackend.exception.MyOwnException;
import com.josgongon9.tfgwebbackend.model.User;
import com.josgongon9.tfgwebbackend.model.Vacation;
import com.josgongon9.tfgwebbackend.model.response.VacationResponse;
import com.josgongon9.tfgwebbackend.repository.VacationRepository;
import com.josgongon9.tfgwebbackend.service.IVacationService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class VacationServiceImpl extends BasicServiceImpl implements IVacationService {

    @Autowired
    VacationRepository vacationRepository;


    public Vacation createVacation(VacationResponse vacation) throws MyOwnException {
        Vacation _vacation = vacationRepository.save(new Vacation(vacation.getId(), vacation.getTitle(), vacation.getDescription(), false, vacation.getStartDate(), vacation.getEndDate(), "PROPOUSE"));
        //int diff =  countBusinessDaysBetween(vacation.getStartDate(), vacation.getEndDate());
        User user = this.getUser();
        user.getVacations().add(_vacation);
        Integer daysOfV = user.getDaysOfVacations();
        if (!Objects.isNull(daysOfV) && daysOfV >= vacation.getResultDate()) {
            user.setDaysOfVacations(daysOfV - vacation.getResultDate());
        } else {
            throw new MyOwnException("No pueden solicitarse más días de los disponibles");
        }
        userRepository.save(user);
        return _vacation;
    }

    public List<Vacation> getAllByUser(String userId) throws MyOwnException {
        List<Vacation> vacations = new ArrayList<Vacation>();

        User userRes = userRepository.findById(userId).orElseThrow(() -> new MyOwnException("Usuario no encontrado"));

        vacations = userRes.getVacations();

        return vacations;
    }


    public void deleteVacation(String id) {
        ObjectId idVac = new ObjectId(id);
        List<User> userList = userRepository.findUserByVacation(idVac);
        for (User user : userList) {
            user.getVacations().removeIf(x -> x.getId().equals(id));
            userRepository.save(user);
        }

        vacationRepository.deleteById(id);
    }

    private static int countBusinessDaysBetween(final Date startDateRes,
                                                final Date endDateRes) {
        LocalDate startDate =
                startDateRes.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
        LocalDate endDate =
                endDateRes.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();


        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Invalid method argument(s) to countBusinessDaysBetween (" + startDate + "," + endDate + "," + ")");
        }


        Predicate<LocalDate> isWeekend = date -> date.getDayOfWeek() == DayOfWeek.SATURDAY
                || date.getDayOfWeek() == DayOfWeek.SUNDAY;

        List<LocalDate> businessDays = startDate.datesUntil(endDate)
                .filter(isWeekend.negate())
                .collect(Collectors.toList());

        return businessDays.size();
    }

}
