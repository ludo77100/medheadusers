package com.medhead.usersmicroservice.dtos;

/**
 * DTO d'inscription de l'utilisateur
 *
 * Ce DTO est utilisé pour transférer les données d'inscription d'un utilisateur
 *
 * Principales fonctionnalités :
 * - Fournit les champs nécessaires à l'inscription d'un utilisateur
 */
 public class RegisterUserDto {
    private String email;

    private String password;

    private String fullName;

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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}