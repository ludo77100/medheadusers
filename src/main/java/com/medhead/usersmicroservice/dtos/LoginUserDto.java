package com.medhead.usersmicroservice.dtos;

/**
 * DTO de connexion de l'utilisateur
 *
 * Ce DTO est utilisé pour transférer les données de connexion de l'utilisateur
 *
 * Principales fonctionnalités :
 * - Fournit les champs nécessaires à la connexion d'un utilisateur
 */
public class LoginUserDto {
    private String email;

    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}