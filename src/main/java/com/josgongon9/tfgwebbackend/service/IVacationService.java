package com.josgongon9.tfgwebbackend.service;

import com.josgongon9.tfgwebbackend.exception.MyOwnException;
import com.josgongon9.tfgwebbackend.model.Vacation;

import java.util.List;

public interface IVacationService {
     Vacation createVacation(Vacation vacation);

     List<Vacation> getAllByUser(String userId) throws MyOwnException;

      void deleteVacation(String id);

}
