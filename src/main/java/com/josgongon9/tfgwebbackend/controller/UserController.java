package com.josgongon9.tfgwebbackend.controller;

import com.josgongon9.tfgwebbackend.exception.MyOwnException;
import com.josgongon9.tfgwebbackend.model.ERole;
import com.josgongon9.tfgwebbackend.model.Role;
import com.josgongon9.tfgwebbackend.model.User;
import com.josgongon9.tfgwebbackend.model.response.UserResponse;
import com.josgongon9.tfgwebbackend.repository.RoleRepository;
import com.josgongon9.tfgwebbackend.repository.UserRepository;
import com.josgongon9.tfgwebbackend.service.impl.UserServiceImpl;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserServiceImpl userServiceImpl;


    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public String userAccess() {
        return "User Content.";
    }

    @GetMapping("/mod")
    @PreAuthorize("hasRole('MODERATOR')")
    public String moderatorAccess() {
        return "Moderator Board.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }

    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/listUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = new ArrayList<User>();

            userRepository.findAll().forEach(users::add);


            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listMod")
    public ResponseEntity<List<User>> getAllMod() {
        try {
            List<User> modList = new ArrayList<User>();
            Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            ObjectId obj = new ObjectId(modRole.getId());

            modList = userRepository.findAllByRole(obj);


            if (modList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(modList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/AllByRol/{role}")
    public ResponseEntity<List<User>> getAllByRol(@PathVariable String role) {
        try {
            ERole erole = ERole.valueOf(role);
            List<User> rolList = new ArrayList<User>();
            Role modRole = roleRepository.findByName(erole).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            ObjectId obj = new ObjectId(modRole.getId());

            rolList = userRepository.findAllByRole(obj);


            if (rolList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(rolList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findByUsername")
    public ResponseEntity<List<User>> findByUsername(@RequestParam("username") String username) {
        try {
            List<User> userList = new ArrayList<User>();
            userList = userRepository.findAllByUsernameLike(username);


            if (userList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(userList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/findByUserAndRol")
    public ResponseEntity<List<User>> getAllByRol(@RequestParam("username") String username,@RequestParam("role") String role) {
        try {
            ERole erole = ERole.valueOf(role);
            List<User> rolList = new ArrayList<User>();
            Role modRole = roleRepository.findByName(erole).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            ObjectId obj = new ObjectId(modRole.getId());

            rolList = userRepository.findAllByRolesAndUsernameLike(obj, username);


            if (rolList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(rolList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity findById(@PathVariable("id") String id) {
        try {
            User userRes = userRepository.findById(id).orElseThrow(() -> new MyOwnException("Usuario no encontrado"));

            return new ResponseEntity<>(userRes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/moderadoresByOrganization")
    public ResponseEntity findModeradoresByOrganization(@RequestParam("idOrg") String sIdOrg) {
        try {
            List<User> userList = new ArrayList<User>();
            ObjectId idOrg = new ObjectId(sIdOrg);
            userList = userRepository.findUserByOrganizations(idOrg);
            List <String> usertListNames = userList.stream().map(n -> n.getUsername()).collect(Collectors.toList());

            if (userList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(usertListNames, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/exportData/{id}")
    public ResponseEntity exportData(@PathVariable("id") String id) {
        try {

            return new ResponseEntity<>(userServiceImpl.generateDataToExport(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity deleteVacation(@PathVariable("id") String id) {
        try {
            userRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity updateVacation(@PathVariable("id") String id, @RequestBody UserResponse user) {
        try {
            return new ResponseEntity<>(userServiceImpl.update(id, user), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
