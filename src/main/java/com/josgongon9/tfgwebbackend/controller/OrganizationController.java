package com.josgongon9.tfgwebbackend.controller;

import com.josgongon9.tfgwebbackend.model.Organization;
import com.josgongon9.tfgwebbackend.model.User;
import com.josgongon9.tfgwebbackend.model.response.OrganizationResponse;
import com.josgongon9.tfgwebbackend.repository.OrganizationRepository;
import com.josgongon9.tfgwebbackend.repository.UserRepository;
import com.josgongon9.tfgwebbackend.security.jwt.JwtUtils;
import com.josgongon9.tfgwebbackend.service.IOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/organization")
public class OrganizationController {

    @Autowired
    IOrganizationService organizationService;

    @Autowired
    OrganizationRepository organizationRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtUtils jwtUtils;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<Organization>> getAllOrganizations(@RequestHeader String authorization) {
        try {

            List<Organization> organizations = organizationRepository.findAll();

            if (organizations.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(organizations, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<Organization> createOrganization(@RequestBody OrganizationResponse organizationResponse) {
        try {
            return new ResponseEntity<>(organizationService.createOrganization(organizationResponse), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

}
