package com.samir.taskmanager.service;

import com.samir.taskmanager.entity.TaskTag;

import java.util.List;

public interface TaskTagService {
    List<TaskTag> getAllTaskTags(String sortBy);
}
