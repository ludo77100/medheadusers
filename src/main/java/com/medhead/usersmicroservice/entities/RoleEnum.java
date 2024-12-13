package com.medhead.usersmicroservice.entities;

/**
 *
 * Cette énumération représente les différents rôles d'utilisateur disponibles dans le système
 *
 * Liste des rôles disponibles :
 * - USER : Rôle d'utilisateur standard
 * - ADMIN : Rôle d'administrateur avec plus de privilèges
 * - SUPER_ADMIN : Rôle de super administrateur ayant tous les droits sur le système
 */
 public enum RoleEnum {
    USER,
    ADMIN,
    SUPER_ADMIN
}
