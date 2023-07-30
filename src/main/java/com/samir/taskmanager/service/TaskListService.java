package com.samir.taskmanager.service;

import com.samir.taskmanager.dto.TaskList;

public interface TaskListService {
    public TaskList getTaskLists();
    public String getHierarchicalTaskListString(TaskList rootTaskList);
    public String getHierarchicalTaskListStringWithStyle(TaskList rootTaskList);
    public String getHirearchicalTaskListStringWithAnotherStyle(TaskList rootTaskList);

}
