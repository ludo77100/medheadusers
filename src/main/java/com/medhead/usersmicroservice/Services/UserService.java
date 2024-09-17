package com.medhead.usersmicroservice.Services;


import com.medhead.usersmicroservice.Dtos.RegisterUserDto;
import com.medhead.usersmicroservice.Entities.Role;
import com.medhead.usersmicroservice.Entities.RoleEnum;
import com.medhead.usersmicroservice.Repositories.RoleRepository;
import com.medhead.usersmicroservice.Repositories.UserRepository;
import com.medhead.usersmicroservice.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    RoleRepository roleRepository ;

    @Autowired
    UserRepository userRepository ;

    private final PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }

    public User createAdministrator(RegisterUserDto input) {

        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.ADMIN);

        if (optionalRole.isEmpty()) {
            return null;
        }

        var user = new User();
                user.setFullName(input.getFullName());
                user.setEmail(input.getEmail());
                user.setPassword(passwordEncoder.encode(input.getPassword()));
                user.setRole(optionalRole.get());

        return userRepository.save(user);
    }
}