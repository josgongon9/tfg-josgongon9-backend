package com.josgongon9.tfgwebbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Document(collection = "organizations")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
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

    public Organization(String name, String description, String phoneNumber, String url) {
        this.name = name;
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.url = url;
        this.f_alta = new Date();
    }
}
