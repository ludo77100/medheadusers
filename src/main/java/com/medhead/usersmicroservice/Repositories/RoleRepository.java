package com.medhead.usersmicroservice.Repositories;

import com.medhead.usersmicroservice.Entities.Role;
import com.medhead.usersmicroservice.Entities.RoleEnum;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
    Optional<Role> findByName(RoleEnum name);
}