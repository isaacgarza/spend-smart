package com.spendsmart.repository;

import com.spendsmart.entity.UserSubcategoryTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface UserSubcategoryRepository extends JpaRepository<UserSubcategoryTable, UUID> {
    Set<UserSubcategoryTable> findAllByPersonId(UUID userId);
}
