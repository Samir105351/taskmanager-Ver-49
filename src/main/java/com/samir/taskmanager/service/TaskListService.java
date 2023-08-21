package com.samir.taskmanager.service;

import com.samir.taskmanager.dto.TaskList;
import com.samir.taskmanager.entity.Sister;

public interface TaskListService {
    public TaskList getTreeLists();
    public TaskList getTreeLists(Sister sister);
    public String getHierarchicalTaskListString(TaskList rootTaskList);
    public String getHierarchicalTaskListStringWithStyle(TaskList rootTaskList);
    public String getHirearchicalTaskListStringWithAnotherStyle(TaskList rootTaskList);

}
