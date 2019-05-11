package com.spendsmart.repository;

import com.spendsmart.entity.SubcategoryTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface SubcategoryRepository extends JpaRepository<SubcategoryTable, UUID> {
    Set<SubcategoryTable> findAllByCategoryId(UUID categoryId);
}
