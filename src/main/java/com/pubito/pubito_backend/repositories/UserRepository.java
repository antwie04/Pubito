package com.pubito.pubito_backend.repositories;

import com.pubito.pubito_backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
