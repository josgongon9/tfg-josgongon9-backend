package com.josgongon9.tfgwebbackend.service.impl;

import com.josgongon9.tfgwebbackend.exception.MyOwnException;
import com.josgongon9.tfgwebbackend.model.ERole;
import com.josgongon9.tfgwebbackend.model.Role;
import com.josgongon9.tfgwebbackend.model.User;
import com.josgongon9.tfgwebbackend.model.response.UserResponse;
import com.josgongon9.tfgwebbackend.repository.RoleRepository;
import com.josgongon9.tfgwebbackend.repository.UserRepository;
import com.josgongon9.tfgwebbackend.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    public String generateDataToExport(String id) throws MyOwnException {
        User user = userRepository.findById(id).orElseThrow(() -> new MyOwnException("Usuario no encontrado"));

        return user.toString();
    }

    @Transactional
    public UserResponse update(String id, UserResponse user) throws MyOwnException {
        User userB = userRepository.findById(id).orElseThrow(() -> new MyOwnException("Usuario no encontrado"));
        userB.setEmail(user.getEmail());
        userB.setDaysOfVacations(user.getDaysOfVacations());
        Set<Role> rolesAdd = new HashSet<>();
        userB.setRoles(rolesAdd);

        for (String r : user.getRoles()) {
            Role roleSearch = roleRepository.findByName(ERole.valueOf(r))
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            rolesAdd.add(roleSearch);
        }
        userB.setRoles(rolesAdd);
        UserResponse userRes = new UserResponse(userB);
        userRepository.save(userB);
        return userRes;
    }

}
