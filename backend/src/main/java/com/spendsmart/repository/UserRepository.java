package com.spendsmart.repository;

import com.spendsmart.entity.UserTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserTable, UUID> {

    Optional<UserTable> findByEmail(String email);
}
