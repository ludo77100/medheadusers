package com.medhead.usersmicroservice.controllers;


import com.medhead.usersmicroservice.dtos.RegisterUserDto;
import com.medhead.usersmicroservice.entities.User;
import com.medhead.usersmicroservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Contrôleur de gestion des administrateurs
 *
 * Ce contrôleur expose les endpoints pour la gestion des administrateurs.
 *
 * Principales fonctionnalités :
 * - Créer un administrateur : Permet à un Super Administrateur de créer un utilisateur ayant le rôle d'administrateur.
 *
 */
@RequestMapping("/admins")
@RestController
public class AdminController {

    @Autowired
    UserService userService ;

    /**
     * Créer un administrateur
     *
     * Ce point d'entrée permet à un Super Administrateur de créer un nouvel utilisateur avec le rôle ADMIN
     *
     * @param registerUserDto DTO de l'utilisateur contenant les informations de l'administrateur à créer.
     *
     * @return ResponseEntity contenant l'utilisateur nouvellement créé
     */
    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<User> createAdministrator(@RequestBody RegisterUserDto registerUserDto) {
        User createdAdmin = userService.createAdministrator(registerUserDto);

        return ResponseEntity.ok(createdAdmin);
    }
}