package com.medhead.usersmicroservice.services.impl;

import com.medhead.usersmicroservice.entities.User;
import com.medhead.usersmicroservice.repositories.UserRepository;
import com.medhead.usersmicroservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl extends UserService {

    @Autowired
    UserRepository userRepository ;

    public UserServiceImpl(PasswordEncoder passwordEncoder) {
        super(passwordEncoder);
    }

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }

}
