package com.medhead.usersmicroservice.bootstrap;

import com.medhead.usersmicroservice.entities.Role;
import com.medhead.usersmicroservice.entities.RoleEnum;
import com.medhead.usersmicroservice.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * RoleSeeder : Initialisation des rôles utilisateur lors du démarrage de l'application.
 *
 * Cette classe écoute l'événement ContextRefreshedEvent et initialise les rôles nécessaires pour l'application.
 *
 * Principales fonctionnalités :
 * - Vérifie si les rôles USER, ADMIN et SUPER_ADMIN existent.
 * - Si un rôle n'existe pas, il est créé et enregistré en base de données.
 *
 */
@Component
public class RoleSeeder implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.loadRoles();
    }


    /**
     * Création des rôles de l'application.
     *
     * Logique principale :
     * - Crée les rôles USER, ADMIN et SUPER_ADMIN s'ils n'existent pas.
     * - Pour chaque rôle, vérifie s'il est déjà présent dans la base de données.
     * - Si le rôle n'existe pas, il est créé et sauvegardé.
     *
     */
    void loadRoles() {
        RoleEnum[] roleNames = new RoleEnum[] { RoleEnum.USER, RoleEnum.ADMIN, RoleEnum.SUPER_ADMIN };
        Map<RoleEnum, String> roleDescriptionMap = Map.of(
                RoleEnum.USER, "Default user role",
                RoleEnum.ADMIN, "Administrator role",
                RoleEnum.SUPER_ADMIN, "Super Administrator role"
        );

        Arrays.stream(roleNames).forEach(roleName -> {
            Optional<Role> optionalRole = roleRepository.findByName(roleName);

            optionalRole.ifPresentOrElse(System.out::println, () -> {
                Role roleToCreate = new Role();

                roleToCreate.setName(roleName);
                roleToCreate.setDescription(roleDescriptionMap.get(roleName));

                roleRepository.save(roleToCreate);
            });
        });
    }
}