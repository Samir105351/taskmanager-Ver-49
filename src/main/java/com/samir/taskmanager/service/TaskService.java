package com.samir.taskmanager.service;

import com.samir.taskmanager.dto.TaskSisterTaskTag;
import com.samir.taskmanager.dto.TaskTaskTag;
import com.samir.taskmanager.entity.Task;

import java.util.List;

public interface TaskService {
    List<Task> getAllTasks();

    Task saveTask(Task task);

    Task getTaskById(Long id);

    Task updateTask(Task task);

    void deleteTaskById(Long id);

    void deleteAllTasks();

    List<TaskSisterTaskTag> getAllTaskSisterTaskTags(Long columnNumber);

    List<TaskTaskTag> getAllTaskTaskTagUnderASisterId(Long id, Long columnNumber);
}
