package com.josgongon9.tfgwebbackend;

import com.josgongon9.tfgwebbackend.model.ERole;
import com.josgongon9.tfgwebbackend.model.Organization;
import com.josgongon9.tfgwebbackend.model.Role;
import com.josgongon9.tfgwebbackend.model.User;
import com.josgongon9.tfgwebbackend.repository.OrganizationRepository;
import com.josgongon9.tfgwebbackend.repository.UserRepository;
import com.josgongon9.tfgwebbackend.service.impl.BasicServiceImpl;
import com.josgongon9.tfgwebbackend.service.impl.OrganizationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrganizationsTests {
    @InjectMocks
    OrganizationServiceImpl organizationService;

    @Mock
    OrganizationRepository organizationRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    Authentication authentication;
    @Mock
    SecurityContext securityContext;
    @Mock
    BasicServiceImpl basicService;

    private final User user = new User("Josemi", "prueba@gmail.com", "12345678");
    private final User modUser = new User("UsuModerador", "morerador@gmail.com", "12345678");
    private final List<User> modListUsers = new ArrayList<User>();
    private final Organization organization = new Organization("1", "Mi organizacion", "Descripcion de prueba", "685254120", "https://es.wikipedia.org/wiki/Wikipedia:Portada", "España", "Sevilla", "Sevilla");
    private final Set<Role> rolAdmin = new HashSet<Role>();
    private final Role admin = new Role(ERole.ROLE_ADMIN);

    //Dar de alta Organizaciones
    /*@Test
    void createOrganizationKO() {
        // Given
        modListUsers.add(modUser);

        // When
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(userRepository.findByUsername(null)).thenReturn(Optional.of(user));

        // Then
        Exception exception = assertThrows(Exception.class, () -> {
            organizationService.createOrganization(organization);
        });

        String expectedMessage = "Usuario no permitido";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    //Solos los administradores pueden dar de alta
    @Test
    void createOrganizationAdminOK() throws Exception {
        // Given
        rolAdmin.add(admin);
        user.setRoles(rolAdmin);
        modListUsers.add(modUser);

        // When
        when(organizationRepository.save(Mockito.any(Organization.class))).thenReturn(organization);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(userRepository.findByUsername(null)).thenReturn(Optional.of(user));

        Organization createVacation = organizationService.createOrganization(organization);

        // Then
        Assertions.assertNotNull(createVacation.getId());
    }


    //Usuario asignado no permitido (diferente a un moderador)
    @Test
    void createOrganizationModKO() throws Exception {
        // Given
        rolAdmin.add(admin);
        user.setRoles(rolAdmin);
        modListUsers.add(user);

        // When
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(userRepository.findByUsername(null)).thenReturn(Optional.of(user));

        // Then
        Exception exception = assertThrows(Exception.class, () -> {
            organizationService.createOrganization(organization);
        });

        String expectedMessage = "Moderadores no permitido";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }*/


}
