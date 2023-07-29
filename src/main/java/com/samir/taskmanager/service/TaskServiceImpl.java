package com.samir.taskmanager.service;

import com.samir.taskmanager.Utility.NullChecker;
import com.samir.taskmanager.dto.TaskSisterTaskTag;
import com.samir.taskmanager.dto.TaskTaskTag;
import com.samir.taskmanager.entity.Task;
import com.samir.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll(Sort.by(Sort.Direction.ASC, "taskID"));
    }

    @Override
    public Task saveTask(Task task) {
        task.setTaskName(NullChecker.check(task.getTaskName()));
        return taskRepository.save(task);
    }

    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).get();
    }

    @Override
    public Task updateTask(Task task) {
        task.setTaskName(NullChecker.check(task.getTaskName()));
        return taskRepository.save(task);
    }

    @Override
    public void deleteTaskById(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public void deleteAllTasks() {
        taskRepository.deleteAll();
    }

    @Override
    public List<TaskSisterTaskTag> getAllTaskSisterTaskTags(Long columnNumber) {
        return taskRepository.getJoinTaskServiceTaskTag(columnNumber);
    }

    @Override
    public List<TaskTaskTag> getAllTaskTaskTagUnderASisterId(Long id, Long columnNumber) {
        return taskRepository.getJoinTaskTaskTagBySystemId(id, columnNumber);
    }

}
