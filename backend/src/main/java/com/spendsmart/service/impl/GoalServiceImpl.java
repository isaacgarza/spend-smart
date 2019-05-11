package com.spendsmart.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spendsmart.cache.Cache;
import com.spendsmart.dto.Goal;
import com.spendsmart.entity.GoalTable;
import com.spendsmart.repository.GoalRepository;
import com.spendsmart.service.GoalService;
import com.spendsmart.service.ServiceException;
import com.spendsmart.util.ExceptionConstants;
import com.spendsmart.util.FundingScheduleEnum;
import com.spendsmart.util.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class GoalServiceImpl implements GoalService {

    private final GoalRepository goalRepository;
    private final Cache cache;
    private final ObjectMapper jacksonObjectMapper;

    @Autowired
    public GoalServiceImpl(GoalRepository goalRepository,
                           Cache cache,
                           @Qualifier("jacksonObjectMapper") ObjectMapper objectMapper) {
        this.goalRepository = goalRepository;
        this.cache = cache;
        this.jacksonObjectMapper = objectMapper;
    }

    @Transactional
    public Goal addGoal(Goal goal) {
        GoalTable goalTable = mapGoalToTable(goal);
        save(goal, goalTable);
        goal.setId(goalTable.getId());
        goal.setCreatedTimestamp(goalTable.getCreatedTimestamp());
        goal.setUpdatedTimestamp(goalTable.getUpdatedTimestamp());
        return goal;
    }

    @Transactional
    public void updateGoal(Goal goal) {
        GoalTable newGoalTable = mapGoalToTable(goal);
        Optional<GoalTable> goalTable = goalRepository.findById(goal.getId());
        if (goalTable.isPresent()) {
            ServiceUtil.copyNonNullProperties(newGoalTable, goalTable.get());
            save(goal, goalTable.get());
        } else {
            throw new ServiceException(ExceptionConstants.GOAL_NOT_FOUND);
        }
    }

    @Transactional
    public void deleteGoal(UUID goalId) {
        try {
            goalRepository.deleteById(goalId);
        } catch (Exception e) {
            throw new ServiceException("Exception occurred deleting goal", e);
        }
    }

    @Transactional(readOnly = true)
    public Set<Goal> getGoals(UUID personId) {
        try {
            return mapGoalTableListToGoals(goalRepository.findAllByPersonId(personId));
        } catch (Exception e) {
            throw new ServiceException("Exception occurred retrieving goals for " + personId, e);
        }
    }

    private GoalTable mapGoalToTable(Goal goal) {
        jacksonObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return jacksonObjectMapper.convertValue(goal, GoalTable.class);
    }

    private Set<Goal> mapGoalTableListToGoals(Set<GoalTable> goalTableList) {
        jacksonObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Set<Goal> goals = new HashSet<>();
        goalTableList.forEach(goalTable -> {
            Goal goal = jacksonObjectMapper.convertValue(goalTable, Goal.class);
            FundingScheduleEnum fundingScheduleType = cache.getFundingScheduleTypeEnum(goalTable.getFundingScheduleTypeId());
            goal.setFundingScheduleType(fundingScheduleType);
            goals.add(goal);
        });
        return goals;
    }

    private void save(Goal goal, GoalTable goalTable) {
        try {
            UUID fundingScheduleTypeId = cache.getFundingScheduleId(goal.getFundingScheduleType());
            goalTable.setFundingScheduleTypeId(fundingScheduleTypeId);
            goalRepository.save(goalTable);
        } catch (Exception e) {
            throw new ServiceException("Exception occurred adding/updating goal", e);
        }
    }
}
