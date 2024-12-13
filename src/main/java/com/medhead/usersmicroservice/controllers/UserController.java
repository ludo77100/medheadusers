package com.medhead.usersmicroservice.controllers;

import com.medhead.usersmicroservice.entities.User;
import com.medhead.usersmicroservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Contrôleur de gestion des utilisateurs
 *
 * Ce contrôleur expose les endpoints de gestion des utilisateurs.
 */
@RequestMapping("/users")
@RestController
public class UserController {

    @Autowired
    UserService userService ;

/**
 * Récupérer l'utilisateur authentifié
 *
 * Ce point d'entrée permet de récupérer les informations de l'utilisateur actuellement connecté.
 *
 */
 @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        return ResponseEntity.ok(currentUser);
    }

/**
 * Lister tous les utilisateurs
 *
 * Ce point d'entrée permet de récupérer la liste de tous les utilisateurs.
 *
 */
 @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<User>> allUsers() {
        List <User> users = userService.allUsers();

        return ResponseEntity.ok(users);
    }
}