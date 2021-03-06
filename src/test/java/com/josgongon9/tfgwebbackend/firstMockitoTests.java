package com.josgongon9.tfgwebbackend;

import com.josgongon9.tfgwebbackend.exception.MyOwnException;
import com.josgongon9.tfgwebbackend.model.User;
import com.josgongon9.tfgwebbackend.model.Vacation;
import com.josgongon9.tfgwebbackend.model.response.VacationResponse;
import com.josgongon9.tfgwebbackend.repository.UserRepository;
import com.josgongon9.tfgwebbackend.repository.VacationRepository;
import com.josgongon9.tfgwebbackend.service.impl.VacationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class firstMockitoTests {
    //Inyección de dependencias (objeto simulado)
    @Mock
    VacationRepository vacationRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    VacationServiceImpl vacationService;

    @Mock
    Authentication authentication;
    @Mock
    SecurityContext securityContext;

    @Test
    void givenValidUser_whenSaveUser_thenSucceed() throws MyOwnException {
        // Given
        Vacation vacation = new Vacation("1", "Prueba", "Prueba Des", false, new Date(), new Date(), "PROPUESE");
        VacationResponse vacationR = new VacationResponse(vacation);
        User user = new User("Josemi", "prueba@gmail.com", "12345678");
        when(vacationRepository.save(Mockito.any(Vacation.class))).thenReturn(vacation);
        when(userRepository.save(Mockito.any(User.class))).thenReturn(null);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(userRepository.findByUsername(null)).thenReturn(Optional.of(user));


        // When
        Vacation createVacation = vacationService.createVacation(vacationR);

        // Then
        assertNotNull(createVacation.getId());

    }

}
