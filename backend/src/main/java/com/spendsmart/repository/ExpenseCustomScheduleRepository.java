package com.spendsmart.repository;

import com.spendsmart.entity.ExpenseCustomScheduleTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ExpenseCustomScheduleRepository extends JpaRepository<ExpenseCustomScheduleTable, UUID> {

}
