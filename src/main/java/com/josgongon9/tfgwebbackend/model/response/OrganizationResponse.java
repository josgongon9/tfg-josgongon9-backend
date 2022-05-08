package com.josgongon9.tfgwebbackend.model.response;

import com.josgongon9.tfgwebbackend.model.Organization;
import com.josgongon9.tfgwebbackend.model.User;
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
    private List<User> usuarios;

    public OrganizationResponse(Organization organization, List<String> moderador) {
        this.id = organization.getId();
        this.name = organization.getName();
        this.description = organization.getDescription();
        this.f_alta = organization.getF_alta();
        this.f_baja = organization.getF_baja();
        this.phoneNumber = organization.getPhoneNumber();
        this.url = organization.getUrl();
        this.country = organization.getCountry();
        this.province = organization.getProvince();
        this.city = organization.getCity();
        this.moderador = moderador;

    }
}
