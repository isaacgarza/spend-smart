package com.spendsmart.service;

import com.spendsmart.dto.Goal;

import java.util.Set;
import java.util.UUID;

public interface GoalService {

    Goal addGoal(Goal goal);

    void updateGoal(Goal goal);

    void deleteGoal(UUID goalId);

    Set<Goal> getGoals(UUID personId);
}
