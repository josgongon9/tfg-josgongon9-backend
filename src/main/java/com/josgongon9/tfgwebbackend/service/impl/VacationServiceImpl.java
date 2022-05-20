package com.josgongon9.tfgwebbackend.service.impl;

import com.josgongon9.tfgwebbackend.exception.MyOwnException;
import com.josgongon9.tfgwebbackend.model.User;
import com.josgongon9.tfgwebbackend.model.Vacation;
import com.josgongon9.tfgwebbackend.repository.UserRepository;
import com.josgongon9.tfgwebbackend.repository.VacationRepository;
import com.josgongon9.tfgwebbackend.service.IVacationService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VacationServiceImpl extends BasicServiceImpl implements IVacationService{

    @Autowired
    VacationRepository vacationRepository;



    public Vacation createVacation(Vacation vacation) {
        Vacation _vacation = vacationRepository.save(new Vacation(vacation.getId(), vacation.getTitle(), vacation.getDescription(), false, vacation.getStartDate(), vacation.getEndDate(), "PROPOUSE"));
        User user = this.getUser();
        user.getVacations().add(_vacation);
        userRepository.save(user);
        System.out.println("CREADA VACACION");
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
        for (User mod : userList) {
            mod.getOrganizations().removeIf(x -> x.getId().equals(id));
            userRepository.save(mod);
        }

        vacationRepository.deleteById(id);
    }


}
