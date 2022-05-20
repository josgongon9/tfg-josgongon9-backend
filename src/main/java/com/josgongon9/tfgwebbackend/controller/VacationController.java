package com.josgongon9.tfgwebbackend.controller;

import com.josgongon9.tfgwebbackend.model.User;
import com.josgongon9.tfgwebbackend.model.Vacation;
import com.josgongon9.tfgwebbackend.repository.VacationRepository;
import com.josgongon9.tfgwebbackend.service.IVacationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class VacationController {

    @Autowired
    IVacationService vacationService;

    @Autowired
    VacationRepository vacationRepository;


    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/vacations")
    public ResponseEntity getAllByUser(@RequestParam("userId") String userId) {
        try {
            List<Vacation> vacations = vacationService.getAllByUser(userId);


            if (vacations.isEmpty() || Objects.isNull(vacations)) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(vacations, HttpStatus.OK);
        } catch (Exception e) {
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/vacationsAll")
    public ResponseEntity<List<Vacation>> getAllVacations(@RequestParam(required = false) String title) {
        try {
            List<Vacation> vacations = new ArrayList<Vacation>();

            if (title == null)
                vacationRepository.findAll().forEach(vacations::add);
            else
                vacationRepository.findByTitleContaining(title).forEach(vacations::add);


            if (vacations.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(vacations, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/vacations/{id}")
    public ResponseEntity<Vacation> getVacationById(@PathVariable("id") String id) {
        Optional<Vacation> vacationData = vacationRepository.findById(id);

        if (vacationData.isPresent()) {
            return new ResponseEntity<>(vacationData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @PostMapping("/vacations")
    public ResponseEntity<Vacation> createVacation(@RequestBody Vacation vacation) {
        try {

            return new ResponseEntity<>(vacationService.createVacation(vacation), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @PutMapping("/vacations/{id}")
    public ResponseEntity<Vacation> updateVacation(@PathVariable("id") String id, @RequestBody Vacation vacation) {
        Optional<Vacation> vacationData = vacationRepository.findById(id);

        if (vacationData.isPresent()) {
            Vacation _vacation = vacationData.get();
            _vacation.setTitle(vacation.getTitle());
            _vacation.setDescription(vacation.getDescription());
            _vacation.setPublished(vacation.isPublished());
            return new ResponseEntity<>(vacationRepository.save(_vacation), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @DeleteMapping("/vacations/{id}")
    public ResponseEntity<HttpStatus> deleteVacation(@PathVariable("id") String id) {
        try {
            vacationService.deleteVacation(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @DeleteMapping("/vacations")
    public ResponseEntity<HttpStatus> deleteAllVacations() {
        try {
            vacationRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/vacations/published")
    public ResponseEntity<List<Vacation>> findByPublished() {
        try {
            List<Vacation> vacations = vacationRepository.findByPublished(true);

            if (vacations.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(vacations, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findByTitle")
    public ResponseEntity findByTitle(@RequestParam("title") String title) {
        try {
            List<Vacation> vacationList = new ArrayList<Vacation>();
            vacationList = vacationRepository.findAllByTitleLike(title);


            if (vacationList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(vacationList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

