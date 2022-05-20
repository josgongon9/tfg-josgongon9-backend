package com.josgongon9.tfgwebbackend.service;

import com.josgongon9.tfgwebbackend.exception.MyOwnException;
import com.josgongon9.tfgwebbackend.model.Vacation;
import com.josgongon9.tfgwebbackend.model.response.VacationResponse;

import java.util.List;

public interface IVacationService {
     Vacation createVacation(VacationResponse vacation) throws MyOwnException;

     List<Vacation> getAllByUser(String userId) throws MyOwnException;

      void deleteVacation(String id);

}
