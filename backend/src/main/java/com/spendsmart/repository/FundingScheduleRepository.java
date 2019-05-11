package com.spendsmart.repository;

import com.spendsmart.entity.FundingScheduleTypeTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FundingScheduleRepository extends JpaRepository<FundingScheduleTypeTable, UUID> {

}
