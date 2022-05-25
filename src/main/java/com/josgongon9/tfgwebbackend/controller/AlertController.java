package com.josgongon9.tfgwebbackend.controller;

import com.josgongon9.tfgwebbackend.model.Alert;
import com.josgongon9.tfgwebbackend.repository.AlertRepository;
import com.josgongon9.tfgwebbackend.service.IAlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    @Autowired
    IAlertService alertService;

    @Autowired
    AlertRepository alertRepository;


    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<Alert>> getAllAlerts() {
        try {
            List<Alert> alerts = new ArrayList<Alert>();
            alerts = alertRepository.findAll();

            if (alerts.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(alerts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/alertsByOrg/{idOrg}")
    public ResponseEntity<List<Alert>> getAllByOrg(@PathVariable("idOrg") String idOrg) {
        try {
            List<Alert> alerts = new ArrayList<Alert>();
            alerts = alertService.getAllByOrg(idOrg);

            if (alerts.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(alerts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/alerts/{id}")
    public ResponseEntity<Alert> getAlertById(@PathVariable("id") String id) {
        Optional<Alert> alertData = alertRepository.findById(id);

        if (alertData.isPresent()) {
            return new ResponseEntity<>(alertData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize(" hasRole('MODERATOR') or hasRole('ADMIN')")
    @PostMapping("")
    public ResponseEntity createAlert(@RequestBody Alert alert, @RequestParam("idOrg") String idOrg) {
        try {

            return new ResponseEntity<>(alertService.createAlert(alert, idOrg), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PreAuthorize(" hasRole('MODERATOR') or hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity updateStateAlert(@RequestBody Alert alert, @PathVariable("id") String id) {
        try {

            return new ResponseEntity<>(alertService.updateStateAlert(alert, id), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAlert(@PathVariable("id") String id) {
        try {
            alertService.deleteAlert(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/findByName")
    public ResponseEntity findByName(@RequestParam("name") String name) {
        try {
            List<Alert> alertList = new ArrayList<Alert>();
            alertList = alertRepository.findAllByNameLike(name);


            if (alertList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(alertList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

