package com.josgongon9.tfgwebbackend.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Document(collection = "users")
@Data public class User {
  @Id
  private String id;

  @NotBlank
  @Size(max = 20)
  private String username;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  @NotBlank
  @Size(max = 120)
  private String password;

  private Integer daysOfVacations;

  @DBRef
  private Set<Role> roles = new HashSet<>();
  
  @DBRef(lazy = true)
  private List<TimeEntry> timeEntries = new ArrayList<>();

  @DBRef(lazy = true)
  private List<Vacation> vacations = new ArrayList<>();

  @DocumentReference
  private List<Organization> organizations = new ArrayList<>();

  public User() {
  }

  public User(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }

}