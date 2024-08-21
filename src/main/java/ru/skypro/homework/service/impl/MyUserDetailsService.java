package ru.skypro.homework.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsManager {

    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        return new MyUserPrincipal(user);
    }

    @Override
    public void createUser(UserDetails user) {

    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userByEmail = userRepository.findByEmail(auth.getName());
        if (userByEmail.getCurrentPassword().equals(oldPassword)) {
            userByEmail.setCurrentPassword(newPassword);
        }
        userRepository.save(userByEmail);
    }

    @Override
    public boolean userExists(String username) {
        return false;
    }
}

