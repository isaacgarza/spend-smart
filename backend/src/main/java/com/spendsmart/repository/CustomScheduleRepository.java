package com.spendsmart.repository;

import com.spendsmart.entity.CustomScheduleTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomScheduleRepository extends JpaRepository<CustomScheduleTable, UUID> {

}
