package com.spendsmart.repository;

import com.spendsmart.entity.ExpenseTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseTable, UUID> {
    Set<ExpenseTable> findAllByUserId(UUID userId);
}
