package com.medhead.usersmicroservice.bootstrap;

import com.medhead.usersmicroservice.entities.Role;
import com.medhead.usersmicroservice.entities.RoleEnum;
import com.medhead.usersmicroservice.repositories.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
public class RoleSeederIT {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleSeeder roleSeeder ;


    @Test
    void testLoadRolesCreatesRolesOnApplicationStart() {

        roleSeeder.loadRoles();

        for (RoleEnum roleName : RoleEnum.values()) {
            Optional<Role> role = roleRepository.findByName(roleName);
            assertThat(role).isPresent();
            assertThat(role.get().getName()).isEqualTo(roleName);
            assertThat(role.get().getDescription()).isNotNull();
        }
    }

}
