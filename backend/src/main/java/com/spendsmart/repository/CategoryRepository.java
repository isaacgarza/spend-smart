package com.spendsmart.repository;

import com.spendsmart.entity.CategoryTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryTable, UUID> {
    Optional<CategoryTable> findByName(String category);
}
