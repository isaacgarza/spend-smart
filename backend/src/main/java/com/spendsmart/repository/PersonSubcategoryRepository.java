package com.spendsmart.repository;

import com.spendsmart.entity.PersonSubcategoryTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface PersonSubcategoryRepository extends JpaRepository<PersonSubcategoryTable, UUID> {
    Set<PersonSubcategoryTable> findAllByPersonId(UUID personId);
}
