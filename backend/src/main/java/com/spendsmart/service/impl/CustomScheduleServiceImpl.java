package com.spendsmart.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spendsmart.dto.CustomSchedule;
import com.spendsmart.entity.CustomScheduleTable;
import com.spendsmart.repository.CustomScheduleRepository;
import com.spendsmart.service.CustomScheduleService;
import com.spendsmart.service.ServiceException;
import com.spendsmart.util.ExceptionConstants;
import com.spendsmart.util.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class CustomScheduleServiceImpl implements CustomScheduleService {

    private final CustomScheduleRepository customScheduleRepository;
    private final ObjectMapper jacksonObjectMapper;

    @Autowired
    public CustomScheduleServiceImpl(CustomScheduleRepository customScheduleRepository,
                                      @Qualifier("jacksonObjectMapper") ObjectMapper objectMapper) {
        this.customScheduleRepository = customScheduleRepository;
        this.jacksonObjectMapper = objectMapper;
    }

    @Transactional(readOnly = true)
    public Set<CustomSchedule> getAllCustomSchedules() {
        try {
            return mapCustomScheduleTableListToCustomSchedules(customScheduleRepository.findAll());
        } catch (Exception e) {
            throw new ServiceException("Exception occurred retrieving custom schedules", e);
        }
    }

    @Transactional
    public CustomSchedule addCustomSchedule(CustomSchedule customSchedule) {
        CustomScheduleTable customScheduleTable = mapCustomScheduleToTable(customSchedule);
        save(customScheduleTable);
        customSchedule.setId(customScheduleTable.getId());
        return customSchedule;
    }

    @Transactional
    public void updateCustomSchedule(CustomSchedule customSchedule) {
        CustomScheduleTable newCustomScheduleTable = mapCustomScheduleToTable(customSchedule);
        Optional<CustomScheduleTable> customScheduleTable = customScheduleRepository.findById(customSchedule.getId());
        if (customScheduleTable.isPresent()) {
            ServiceUtil.copyNonNullProperties(newCustomScheduleTable, customScheduleTable.get());
            save(customScheduleTable.get());
        } else {
            throw new ServiceException(ExceptionConstants.CUSTOM_SCHEDULE_NOT_FOUND);
        }
    }

    @Transactional
    public void deleteCustomSchedule(UUID customScheduleId) {
        try {
            customScheduleRepository.deleteById(customScheduleId);
        } catch (Exception e) {
            throw new ServiceException("Exception occurred deleting custom schedule", e);
        }
    }

    private void save(CustomScheduleTable customScheduleTable) {
        try {
            customScheduleRepository.save(customScheduleTable);
        } catch (Exception e) {
            throw new ServiceException("Exception occurred adding/updating custom schedule", e);
        }
    }

    private CustomScheduleTable mapCustomScheduleToTable(CustomSchedule customSchedule) {
        return jacksonObjectMapper.convertValue(customSchedule, CustomScheduleTable.class);
    }

    private Set<CustomSchedule> mapCustomScheduleTableListToCustomSchedules(List<CustomScheduleTable> customScheduleTableList) {
        Set<CustomSchedule> customSchedules = new HashSet<>();
        customScheduleTableList.forEach(customScheduleTable ->
                customSchedules.add(jacksonObjectMapper.convertValue(customScheduleTable, CustomSchedule.class)));
        return customSchedules;
    }
}
