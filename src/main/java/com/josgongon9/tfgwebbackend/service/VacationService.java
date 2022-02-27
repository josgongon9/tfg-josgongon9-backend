package com.josgongon9.tfgwebbackend.service;

import com.josgongon9.tfgwebbackend.model.User;
import com.josgongon9.tfgwebbackend.model.Vacation;
import com.josgongon9.tfgwebbackend.repository.UserRepository;
import com.josgongon9.tfgwebbackend.repository.VacationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class VacationService {

    @Autowired
    VacationRepository vacationRepository;

    @Autowired
    UserRepository userRepository;


    public Vacation createVacation(Vacation vacation) {
        Vacation _vacation = vacationRepository.save(new Vacation(vacation.getId(), vacation.getTitle(), vacation.getDescription(), false, vacation.getStartDate(), vacation.getEndDate(), "PROPOUSE"));
        User user = this.getUser();
        user.getVacations().add(_vacation);
        userRepository.save(user);
        return _vacation;
    }


    private User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

    }

}
