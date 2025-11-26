package com.pubito.pubito_backend.repositories;

import com.pubito.pubito_backend.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
