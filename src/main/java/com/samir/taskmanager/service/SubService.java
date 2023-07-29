package com.samir.taskmanager.service;

import com.samir.taskmanager.dto.SubLowTag;
import com.samir.taskmanager.dto.SubTaskTag;
import com.samir.taskmanager.entity.LowLevelSubTask;

import java.util.List;

public interface SubService {
    List<SubLowTag> getJoinSubLowTag(Long columnName);

    List<SubTaskTag> getJoinSubTaskTagByALlt(Long id, Long columnName);

    LowLevelSubTask saveSubTask(LowLevelSubTask lowLevelSubTask);

    LowLevelSubTask updateSubTask(LowLevelSubTask lowLevelSubTask);

    LowLevelSubTask getSubTaskById(Long id);

    void deleteSubTaskById(Long id);

    void deleteAllLowLevelSubTasks();
}
