package com.spendsmart.repository;

import com.spendsmart.entity.ExpenseRepeatScheduleTypeTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ExpenseRepeatScheduleRepository extends JpaRepository<ExpenseRepeatScheduleTypeTable, UUID> {

}
