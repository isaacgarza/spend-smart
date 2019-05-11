package com.spendsmart.service;

import com.spendsmart.dto.CustomSchedule;

import java.util.Set;
import java.util.UUID;

public interface CustomScheduleService {

    Set<CustomSchedule> getAllCustomSchedules();

    CustomSchedule addCustomSchedule(CustomSchedule customSchedule);

    void updateCustomSchedule(CustomSchedule customSchedule);

    void deleteCustomSchedule(UUID customScheduleId);
}
