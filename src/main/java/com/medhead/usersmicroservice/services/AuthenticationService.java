package com.medhead.usersmicroservice.services;

import com.medhead.usersmicroservice.dtos.LoginUserDto;
import com.medhead.usersmicroservice.dtos.RegisterUserDto;
import com.medhead.usersmicroservice.entities.Role;
import com.medhead.usersmicroservice.entities.RoleEnum;
import com.medhead.usersmicroservice.entities.User;
import com.medhead.usersmicroservice.repositories.RoleRepository;
import com.medhead.usersmicroservice.repositories.UserRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
/**
 * Service d'authentification
 *
 * Ce service fournit les méthodes d'inscription et de connexion pour les utilisateurs
 *
 * Principales fonctionnalités :
 * - Inscription d'un nouvel utilisateur.
 * - Authentification d'un utilisateur existant.
 *
 */
@Service
public class AuthenticationService {

    @Autowired
    RoleRepository roleRepository ;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     *
     * Cette méthode permet de créer un utilisateur et de l'enregistrer dans la base de données
     *
     * @param input DTO d'inscription contenant les informations de l'utilisateur
     *
     * @return Utilisateur créé et enregistré dans la base de données
     */
    public User signup(RegisterUserDto input) {
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.USER);

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

    /**
     *
     * Cette méthode permet de vérifier les informations de connexion de l'utilisateur
     *
     * @param input DTO de connexion contenant l'email et le mot de passe de l'utilisateur
     *
     * @return Utilisateur authentifié s'il existe et que les identifiants sont corrects
     */
    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }
}