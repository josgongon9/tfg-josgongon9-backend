package com.josgongon9.tfgwebbackend.model.response;

import com.josgongon9.tfgwebbackend.model.Organization;
import com.josgongon9.tfgwebbackend.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String id;
    private String username;
    private String email;
    private Integer daysOfVacations;
    private List<String> roles = new ArrayList<>();

    public UserResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.daysOfVacations = user.getDaysOfVacations();
        this.roles = user.getRoles().stream().map(r -> r.getName().name()).collect(Collectors.toList());

    }
}
