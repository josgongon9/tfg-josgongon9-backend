package com.josgongon9.tfgwebbackend.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class OrganizationResponse {
    private String id;
    private String name;
    private String description;
    private Date f_alta;
    private Date f_baja;
    private String phoneNumber;
    private String url;
    private String country;
    private String province;
    private String city;
    private List<String> moderador;
}
