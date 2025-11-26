package com.pubito.pubito_backend.repositories;

import com.pubito.pubito_backend.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
