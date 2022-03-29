package com.josgongon9.tfgwebbackend;

import com.josgongon9.tfgwebbackend.model.Organization;
import com.josgongon9.tfgwebbackend.repository.OrganizationRepository;
import com.josgongon9.tfgwebbackend.service.IOrganizationService;
import com.josgongon9.tfgwebbackend.service.impl.OrganizationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrganizationsTests {
    @InjectMocks
    OrganizationServiceImpl organizationService;

    @Mock
    OrganizationRepository organizationRepository;

    //Dar de alta Organizaciones -> Administradores
    @Test
    void createOrganizationOK() {
        // Given
        Organization organization = new Organization("Mi organizacion", "Descripcion de prueba","685254120", "https://es.wikipedia.org/wiki/Wikipedia:Portada");
        // When
        when(organizationRepository.save(Mockito.any(Organization.class))).thenReturn(organization);

        Organization createVacation = organizationService.createOrganization(organization);

        // Then

    }

}
