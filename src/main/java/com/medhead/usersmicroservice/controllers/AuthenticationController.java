package com.medhead.usersmicroservice.controllers;


import com.medhead.usersmicroservice.dtos.LoginUserDto;
import com.medhead.usersmicroservice.dtos.RegisterUserDto;
import com.medhead.usersmicroservice.entities.User;
import com.medhead.usersmicroservice.responses.LoginResponse;
import com.medhead.usersmicroservice.services.AuthenticationService;
import com.medhead.usersmicroservice.services.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contrôleur d'authentification
 *
 * Ce contrôleur expose les endpoints d'authentification de l'application.
 *
 */
@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    /**
     * Constructeur avec injection des dépendances.
     *
     * @param jwtService Service de gestion des JWT injecté par Spring.
     * @param authenticationService Service d'authentification injecté par Spring.
     */
    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    /**
     * Inscrire un utilisateur
     *
     * Ce point d'entrée permet à un utilisateur de créer un compte.
     *
     * @param registerUserDto DTO d'inscription contenant les informations de l'utilisateur à créer.
     *
     * @return ResponseEntity contenant l'utilisateur nouvellement créé.
     */
    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    /**
     * Authentifier un utilisateur
     *
     * Ce point d'entrée permet à un utilisateur de se connecter et de recevoir un token JWT
     *
     * @param loginUserDto DTO de connexion contenant l'email et le mot de passe de l'utilisateur
     *
     * @return ResponseEntity contenant le token JWT et sa date d'expiration.
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
}