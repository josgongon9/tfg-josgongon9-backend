package com.josgongon9.tfgwebbackend.model;

import com.josgongon9.tfgwebbackend.model.response.OrganizationResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "organizations")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Organization {
    @Id
    @NotNull
    private String id;
    @NotNull
    private String name;
    @NotNull
    private String description;
    private Date f_alta;
    private Date f_baja;
    private String phoneNumber;
    private String url;
    private String country;
    private String province;
    private String city;
    @DocumentReference
    private List<User> usuarios = new ArrayList<>();
    @DBRef(lazy = true)
    private List<Alert> alerts = new ArrayList<>();



    public Organization(String id,String name, String description, String phoneNumber, String url,String country, String province, String city,  List<User> usuarios) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.url = url;
        this.f_alta = new Date();
        this.country = country;
        this.province= province;
        this.city=city;
        this.usuarios = usuarios;

    }

    public Organization(OrganizationResponse org){
        this.id = org.getId();
        this.name = org.getName();
        this.description = org.getDescription();
        this.phoneNumber = org.getPhoneNumber();
        this.url = org.getUrl();
        this.f_alta = org.getF_alta();
        this.country = org.getCountry();
        this.province= org.getProvince();
        this.city=org.getCity();
        this.usuarios = org.getUsuarios();
    }
}
