package com.samir.taskmanager.service;

import com.samir.taskmanager.dto.LltHltTaskTag;
import com.samir.taskmanager.dto.LltTaskTag;
import com.samir.taskmanager.entity.LowLevelTask;

import java.util.List;

public interface LowLevelTaskService {
    List<LltHltTaskTag> getAllLltHltTaskTag(Long columnNumber);

    List<LltTaskTag> getAllLltTaskTagUnderAHlt(Long id, Long columnNumber);

    LowLevelTask saveLowLevelTask(LowLevelTask lowLevelTask);

    LowLevelTask updateLowLevelTask(LowLevelTask lowLevelTask);

    LowLevelTask getLowLevelTaskById(Long id);

    List<LowLevelTask> getAllLowLevelTasks();

    void deleteLowLevelTaskByID(Long id);

    void deleteAllLowLevelTasks();
}
