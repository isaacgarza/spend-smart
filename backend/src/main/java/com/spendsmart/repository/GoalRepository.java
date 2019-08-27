package com.spendsmart.repository;

import com.spendsmart.entity.GoalTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface GoalRepository extends JpaRepository<GoalTable, UUID> {
    Set<GoalTable> findAllByPersonId(UUID userId);
}
