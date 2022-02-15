package com.josgongon9.tfgwebbackend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.josgongon9.tfgwebbackend.model.User;
import com.josgongon9.tfgwebbackend.repository.UserRepository;
import com.josgongon9.tfgwebbackend.repository.VacationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.josgongon9.tfgwebbackend.model.Vacation;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class VacationController {

  @Autowired
  VacationRepository vacationRepository;

  @Autowired
  UserRepository userRepository;

  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  @GetMapping("/vacations")
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
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String username = authentication.getName();
      Vacation _vacation = vacationRepository.save(new Vacation(vacation.getId(),vacation.getTitle(), vacation.getDescription(), false));
      User user = userRepository.findByUsername(username)
              .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

      user.getVacations().add(_vacation);
      userRepository.save(user);
      return new ResponseEntity<>(_vacation, HttpStatus.CREATED);
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
      vacationRepository.deleteById(id);
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

}