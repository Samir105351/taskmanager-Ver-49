package com.samir.taskmanager.service;

import com.samir.taskmanager.dto.TaskList;
import com.samir.taskmanager.entity.HighLevelTask;
import com.samir.taskmanager.entity.LowLevelTask;
import com.samir.taskmanager.entity.Sister;
import com.samir.taskmanager.entity.Task;

public interface TaskListService {
    public TaskList getTreeLists();

    public TaskList getTreeLists(Sister sister);

    public TaskList getTreeLists(Task task);

    public TaskList getTreeLists(HighLevelTask highLevelTask);

    public TaskList getTreeLists(LowLevelTask lowLevelTask);

    public String getHierarchicalTaskListString(TaskList rootTaskList);

    public String getHierarchicalTaskListStringWithStyle(TaskList rootTaskList);

    public String getHirearchicalTaskListStringWithAnotherStyle(TaskList rootTaskList);

}
