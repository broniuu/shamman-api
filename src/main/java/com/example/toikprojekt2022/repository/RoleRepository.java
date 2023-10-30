package com.example.toikprojekt2022.repository;

import com.example.toikprojekt2022.model.Restaurant;
import com.example.toikprojekt2022.model.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface RoleRepository extends CrudRepository<Role, UUID> {
}
