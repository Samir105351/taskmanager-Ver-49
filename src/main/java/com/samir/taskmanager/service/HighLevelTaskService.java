package com.samir.taskmanager.service;

import com.samir.taskmanager.dto.HltTaskTag;
import com.samir.taskmanager.dto.HltTaskTaskTag;
import com.samir.taskmanager.entity.HighLevelTask;

import java.util.List;

public interface HighLevelTaskService {
    List<HighLevelTask> getAllHighLevelTasks();

    HighLevelTask saveHighLevelTask(HighLevelTask highLevelTask);

    HighLevelTask getHighLevelTaskById(Long id);

    HighLevelTask updateHighLevelTask(HighLevelTask highLevelTask);

    void deleteHighTaskById(Long id);

    void deleteAllHighLevelTasks();

    List<HltTaskTaskTag> getAllHltTaskTaskTag(Long columnNumber);

    List<HltTaskTag> getAllHltTaskTagUnderATaskId(Long id, Long columnNumber);

}
