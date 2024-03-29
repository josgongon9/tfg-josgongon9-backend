package com.josgongon9.tfgwebbackend.controller;

import com.josgongon9.tfgwebbackend.model.Organization;
import com.josgongon9.tfgwebbackend.model.response.OrganizationResponse;
import com.josgongon9.tfgwebbackend.repository.OrganizationRepository;
import com.josgongon9.tfgwebbackend.service.IOrganizationService;
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
@RequestMapping("/api/organization")
public class OrganizationController {

    @Autowired
    IOrganizationService organizationService;

    @Autowired
    OrganizationRepository organizationRepository;


    @PreAuthorize(" hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity getAllOrganizations() {
        try {
            List<Organization> organizations = organizationService.getAll();


            if (organizations.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(organizations, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity createOrganization(@RequestBody OrganizationResponse organizationResponse) {
        try {
            return new ResponseEntity<>(organizationService.createOrganization(organizationResponse), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

        }
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Organization> getOrganizationById(@PathVariable("id") String id) {
        Optional<Organization> organization = organizationRepository.findById(id);

        if (organization.isPresent()) {
            return new ResponseEntity<>(organization.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/findByUserId")
    public ResponseEntity getOrganizationByUser(@RequestParam("idUser") String idUser) {


        try {
            return new ResponseEntity<>(organizationService.getOrganizationByUser(idUser), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

        }
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/userOtherOrg")
    public ResponseEntity getUserOtherOrg(@RequestParam("idUser") String idUser, @RequestParam("idOrg") String idOrg) {


        try {
            return new ResponseEntity<>(organizationService.getUserOtherOrg(idOrg), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

        }
    }


    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/organizationsByAlert")
    public ResponseEntity getOrganizationsByAlert() {


        try {
            return new ResponseEntity<>(organizationService.getOrganizationsByAlert(), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

        }
    }




    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity updateOrganization(@PathVariable("id") String id, @RequestBody OrganizationResponse organizationResponse) {
        try {
            return new ResponseEntity<>(organizationService.updateOrganization(organizationResponse), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

        }
    }

    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteOrganization(@PathVariable("id") String id) {
        try {
            organizationService.deleteOrganization(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    @PutMapping("/updateUsers")
    public ResponseEntity updateUsers(@RequestParam("id") String id, @RequestParam("idUser") String idUser) {
        try {
            return new ResponseEntity<>(organizationService.updateUsers(id, idUser), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

        }
    }

    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    @PutMapping("/updateMods")
    public ResponseEntity updateMods(@RequestParam("id") String id, @RequestParam("idUser") String idUser) {
        try {
            organizationService.updateMods(id, idUser);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

        }
    }

    @GetMapping("/findByName")
    public ResponseEntity findByName(@RequestParam("name") String name) {
        try {
            List<Organization> organizationList = new ArrayList<Organization>();
            organizationList = organizationRepository.findAllByNameLike(name);


            if (organizationList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(organizationList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
