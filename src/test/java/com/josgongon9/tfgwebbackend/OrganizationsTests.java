package com.josgongon9.tfgwebbackend;

import com.josgongon9.tfgwebbackend.model.ERole;
import com.josgongon9.tfgwebbackend.model.Organization;
import com.josgongon9.tfgwebbackend.model.Role;
import com.josgongon9.tfgwebbackend.model.User;
import com.josgongon9.tfgwebbackend.model.response.OrganizationResponse;
import com.josgongon9.tfgwebbackend.repository.OrganizationRepository;
import com.josgongon9.tfgwebbackend.repository.UserRepository;
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


    private final User user = new User("Josemi", "prueba@gmail.com", "12345678");
    private final User modUser = new User("UsuModerador", "morerador@gmail.com", "12345678");
    private final List<String> modListUsers = new ArrayList<String>();
    private final List<User> userModList = new ArrayList<>();
    private final Organization organization = new Organization("1", "Mi organizacion", "Descripcion de prueba", "685254120", "https://es.wikipedia.org/wiki/Wikipedia:Portada", "España", "Sevilla", "Sevilla", new ArrayList<>());
    private final OrganizationResponse organizationResponse = new OrganizationResponse(organization, modListUsers);
    private final Set<Role> rolAdmin = new HashSet<Role>();
    private final Role admin = new Role(ERole.ROLE_ADMIN);


    //Dar de alta Organizaciones
    @Test
    void createOrganizationKO() {
        // Given
        modListUsers.add(modUser.getUsername());

        // When
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(userRepository.findByUsername(null)).thenReturn(Optional.of(user));

        // Then
        Exception exception = assertThrows(Exception.class, () -> {
            organizationService.createOrganization(organizationResponse);
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
        modListUsers.add(modUser.getUsername());
        userModList.add(modUser);

        // When
        when(organizationRepository.save(Mockito.any(Organization.class))).thenReturn(organization);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(userRepository.findByUsername(null)).thenReturn(Optional.of(user));
        when(userRepository.findAllByUsernameIn(Mockito.any(List.class))).thenReturn(userModList);

        Organization createOrganization = organizationService.createOrganization(organizationResponse);

        // Then
        Assertions.assertNotNull(createOrganization.getId());
    }


    //Usuario asignado no permitido (diferente a un moderador)
    @Test
    void createOrganizationModKO() throws Exception {
        // Given
        rolAdmin.add(admin);
        user.setRoles(rolAdmin);
        modListUsers.add(user.getUsername());

        // When
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(userRepository.findByUsername(null)).thenReturn(Optional.of(user));

        // Then
        Exception exception = assertThrows(Exception.class, () -> {
            organizationService.createOrganization(organizationResponse);
        });

        String expectedMessage = "Moderadores no permitido";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    //Organizaciones repetidas
    @Test
    void createRepeatOrganization() throws Exception {
        // Given
        rolAdmin.add(admin);
        user.setRoles(rolAdmin);
        modListUsers.add(modUser.getUsername());
        userModList.add(modUser);

        // When
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(userRepository.findByUsername(null)).thenReturn(Optional.of(user));
        when(userRepository.findAllByUsernameIn(Mockito.any(List.class))).thenReturn(userModList);
        when(organizationRepository.findFirstByName(Mockito.any(String.class))).thenReturn(Optional.of(organization));

        // Then
        Exception exception = assertThrows(Exception.class, () -> {
            organizationService.createOrganization(organizationResponse);
        });

        String expectedMessage = "Organización ya existente";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    //Solos los administradores pueden dar de alta
    @Test
    void updateOrganizationOK() throws Exception {
        // Given
        rolAdmin.add(admin);
        user.setRoles(rolAdmin);
        modListUsers.add(modUser.getUsername());
        userModList.add(modUser);

        // When
        when(organizationRepository.save(Mockito.any(Organization.class))).thenReturn(organization);

            //Mockear Seguridad
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(userRepository.findByUsername(null)).thenReturn(Optional.of(user));


        when(userRepository.findAllByUsernameIn(Mockito.any(List.class))).thenReturn(userModList);

        Organization updateOrganization = organizationService.createOrganization(organizationResponse);

        // Then
        Assertions.assertNotNull(updateOrganization.getId());
    }


}
