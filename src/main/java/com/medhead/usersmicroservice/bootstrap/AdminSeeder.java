package com.medhead.usersmicroservice.bootstrap;


import com.medhead.usersmicroservice.dtos.RegisterUserDto;
import com.medhead.usersmicroservice.entities.Role;
import com.medhead.usersmicroservice.entities.RoleEnum;
import com.medhead.usersmicroservice.entities.User;
import com.medhead.usersmicroservice.repositories.RoleRepository;
import com.medhead.usersmicroservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * AdminSeeder : Initialisation du Super Administrateur lors du démarrage de l'application.
 *
 * Cette classe écoute l'événement ContextRefreshedEvent pour initialiser un compte Super Administrateur
 * au démarrage de l'application.
 *
 *
 */
@Component
public class AdminSeeder implements ApplicationListener<ContextRefreshedEvent> {


    @Autowired
    RoleRepository roleRepository ;
    @Autowired
    UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    public AdminSeeder(
            PasswordEncoder passwordEncoder
    ) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.createSuperAdministrator();
    }

    /**
     * Création d'un compte Super Administrateur.
     *
     * Logique principale :
     * - Vérifie si le rôle SUPER_ADMIN existe.
     * - Vérifie si l'utilisateur Super Admin existe déjà.
     * - Si le rôle existe mais que l'utilisateur n'existe pas, il est créé et enregistré.
     *
     */
    private void createSuperAdministrator() {
        RegisterUserDto userDto = new RegisterUserDto();
        userDto.setFullName("Super Admin");
        userDto.setEmail("super.admin@email.com");
        userDto.setPassword("123456");

        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.SUPER_ADMIN);
        Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());

        if (optionalRole.isEmpty() || optionalUser.isPresent()) {
            return;
        }

        var user = new User() ;
        user.setFullName(userDto.getFullName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(optionalRole.get());

        userRepository.save(user);
    }
}