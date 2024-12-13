package com.medhead.usersmicroservice.services;


import com.medhead.usersmicroservice.dtos.RegisterUserDto;
import com.medhead.usersmicroservice.entities.Role;
import com.medhead.usersmicroservice.entities.RoleEnum;
import com.medhead.usersmicroservice.repositories.RoleRepository;
import com.medhead.usersmicroservice.repositories.UserRepository;
import com.medhead.usersmicroservice.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * Ce service fournit la logique métier pour la gestion des utilisateurs
 *
 */
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

    /**
     * Cette méthode renvoie la liste complète des utilisateurs présents dans la base de données.
     *
     * @return Liste des utilisateurs présents dans la base de données.
     */
    public List<User> allUsers() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }

    /**
     * Cette méthode permet de créer un administrateur et de l'enregistrer dans la base de données.
     *
     * @param input DTO d'inscription contenant les informations de l'utilisateur
     *
     * @return Utilisateur créé et enregistré dans la base de données.
     */
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