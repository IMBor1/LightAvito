package ru.skypro.homework.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.impl.MyUserDetailsService;
import ru.skypro.homework.service.impl.MyUserPrincipal;

public class MyUserDetailsServiceTest {

    @InjectMocks
    private MyUserDetailsService userDetailsService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUser() {
        UserDetails userDetails = new MyUserPrincipal(new User("user@example.com", "password", Role.USER));

        userDetailsService.createUser(userDetails);

        verify(userRepository, times(1)).save(any(User.class));
    }


    @Test
    public void testLoadUserByUsername_Success() {
        String email = "user@example.com";
        User user = new User();
        user.setEmail(email);
        user.setCurrentPassword("password");

        when(userRepository.findByEmail(email)).thenReturn(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        String email = "nonexistent@example.com";

        when(userRepository.findByEmail(email)).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(email));
        verify(userRepository, times(1)).findByEmail(email);
    }


    @Test
    public void testChangePassword_Failure() {
        String oldPassword = "wrongOldPassword";
        String newPassword = "newPassword";
        User user = new User();
        user.setCurrentPassword("correctOldPassword");
        user.setEmail("user@example.com");

        when(userRepository.findByEmail("user@example.com")).thenReturn(user);
        SecurityContextHolder.getContext().setAuthentication(new TestingAuthenticationToken(user, null));

        userDetailsService.changePassword(oldPassword, newPassword);

        assertNotEquals(newPassword, user.getCurrentPassword()); // Пароль не должен измениться
        verify(userRepository, times(0)).save(user); // Сохранение не должно произойти
    }
}

