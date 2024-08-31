package ru.skypro.homework.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.exeption.UserNotFaundExeption;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsManager {

    private final PasswordEncoder encoder;

    private final UserRepository userRepository;

    public MyUserDetailsService(PasswordEncoder encoder, UserRepository userRepository) {
        this.encoder = encoder;
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
        return new MyUserPrincipal(user);
    }

    @Override
    public void createUser(UserDetails user) {
        User user1 = new User();
        user1.setCurrentPassword(user.getPassword());
        user1.setEmail(user.getUsername());
        user1.setRole(Role.valueOf(
                user.getAuthorities()
                        .stream()
                        .findFirst()
                        .orElseThrow()
                        .getAuthority()
                        .replace("ROLE_","")));
        userRepository.save(user1);
    }

    @Override
    public void updateUser(UserDetails user) {
        User userEdit = userRepository.findByEmail(user.getUsername()).orElseThrow();
        userEdit.setEmail(user.getUsername());
        userRepository.save(userEdit);
    }

    @Override
    public void deleteUser(String username) {
        User userToDelete = userRepository.findByEmail(username).orElseThrow(UserNotFaundExeption::new);
        userRepository.delete(userToDelete);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName()).orElseThrow(UserNotFaundExeption::new);

        UserDetails userDetails =
               org.springframework.security.core.userdetails.User.builder()
                       .passwordEncoder(this.encoder::encode)
                       .password(newPassword)
                       .username(user.getEmail())
                       .roles(user.getRole().name())
                       .build();

        user.setCurrentPassword(userDetails.getPassword());
        userRepository.save(user);
    }

    @Override
    public boolean userExists(String username) {
        return userRepository.findByEmail(username).isPresent();
    }
}

