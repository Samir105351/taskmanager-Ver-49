package com.samir.taskmanager.service;

import com.samir.taskmanager.Utility.IntegerToRoman;
import com.samir.taskmanager.dto.TaskList;
import com.samir.taskmanager.entity.*;
import com.samir.taskmanager.repository.SisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class TaskListServiceImpl implements TaskListService {
    @Autowired
    private SisterRepository sisterRepository;

    @Override
    public TaskList getTaskLists() {
        TaskList taskList = new TaskList();
        taskList.setTaskName("");

        List<Sister> sisterList = sisterRepository.findAll(Sort.by(Sort.Direction.ASC, "sisterId"));
        List<TaskList> subTaskLists = new ArrayList<>();

        for (Sister sister : sisterList) {
            TaskList sisterTaskList = new TaskList();
            sisterTaskList.setTaskName(sister.getSisterName());

            List<Task> tasks = sister.getTaskList();
            List<TaskList> taskTaskLists = new ArrayList<>();

            for (Task task : tasks) {
                TaskList taskTaskList = new TaskList();
                taskTaskList.setTaskName(task.getTaskName());

                List<HighLevelTask> highLevelTasks = task.getHighLevelTaskList();
                List<TaskList> highLevelTaskLists = new ArrayList<>();

                for (HighLevelTask highLevelTask : highLevelTasks) {
                    TaskList highLevelTaskList = new TaskList();
                    highLevelTaskList.setTaskName(highLevelTask.getHighLevelTaskName());

                    List<LowLevelTask> lowLevelTasks = highLevelTask.getListLowLevelTask();
                    List<TaskList> lowLevelTaskLists = new ArrayList<>();

                    for (LowLevelTask lowLevelTask : lowLevelTasks) {
                        TaskList lowLevelTaskList = new TaskList();
                        lowLevelTaskList.setTaskName(lowLevelTask.getLowLevelTaskName());

                        List<LowLevelSubTask> lowLevelSubtasks = lowLevelTask.getLowLevelSubTaskList();
                        List<TaskList> lowLevelSubtaskLists = new ArrayList<>();

                        for (LowLevelSubTask lowLevelSubtask : lowLevelSubtasks) {
                            TaskList lowLevelSubtaskList = new TaskList();
                            lowLevelSubtaskList.setTaskName(lowLevelSubtask.getLowLevelSubTaskName());
                            lowLevelSubtaskLists.add(lowLevelSubtaskList);
                        }

                        lowLevelTaskList.setTaskLists(lowLevelSubtaskLists);
                        lowLevelTaskLists.add(lowLevelTaskList);
                    }

                    highLevelTaskList.setTaskLists(lowLevelTaskLists);
                    highLevelTaskLists.add(highLevelTaskList);
                }

                taskTaskList.setTaskLists(highLevelTaskLists);
                taskTaskLists.add(taskTaskList);
            }

            sisterTaskList.setTaskLists(taskTaskLists);
            subTaskLists.add(sisterTaskList);
        }

        taskList.setTaskLists(subTaskLists);

        return taskList;
    }

    // Helper method to get directory indicators based on the indentation level and last child status
    private String getDirectoryIndicator(int indentationLevel, boolean isLastChild) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < indentationLevel; i++) {
            sb.append("  | "); // Use vertical bars for intermediate levels (two spaces and a vertical bar for each level)
        }

        sb.append(indentationLevel > 0 ? (isLastChild ? "  └─  " : "  ├─  ") : sb.append(" ")); // Use appropriate ASCII characters for arrows

        return sb.toString();
    }

    // New method to get the hierarchical task list as a string using DFS
    public String getHierarchicalTaskListString(TaskList rootTaskList) {
        Set<TaskList> visited = new HashSet<>();
        StringBuilder resultBuilder = new StringBuilder();
        getHierarchicalTaskListStringHelper(rootTaskList, visited, 0, resultBuilder);
        return resultBuilder.toString();
    }

    // Helper method for generating the hierarchical task list string using DFS
    private void getHierarchicalTaskListStringHelper(TaskList taskList, Set<TaskList> visited, int indentationLevel, StringBuilder resultBuilder) {
        visited.add(taskList);
        resultBuilder.append(taskList.getTaskName()).append("\n");
        if (taskList.getTaskLists() != null) {
            for (int i = 0; i < taskList.getTaskLists().size(); i++) {
                TaskList subTaskList = taskList.getTaskLists().get(i);
                if (!visited.contains(subTaskList)) {
                    String prefix = getDirectoryIndicator(indentationLevel, i == taskList.getTaskLists().size() - 1);
                    resultBuilder.append(prefix); // Append the directory indicator to the resultBuilder
                    getHierarchicalTaskListStringHelper(subTaskList, visited, indentationLevel + 1, resultBuilder);
                }
            }
        }
    }
    @Override
    public String getHierarchicalTaskListStringWithStyle(TaskList rootTaskList) {
        StringBuilder resultBuilder = new StringBuilder();
        getHierarchicalTaskListStringWithStyleHelper(rootTaskList, resultBuilder, 0, new int[]{1, 1, 1, 1});
        return resultBuilder.toString();
    }

    // Helper method for generating the hierarchical task list string with different styles for children
    private void getHierarchicalTaskListStringWithStyleHelper(TaskList taskList, StringBuilder resultBuilder, int indentationLevel, int[] counters) {
        if (taskList == null) {
            return;
        }

        String currentTaskName = taskList.getTaskName();

        // Choose the appropriate prefix based on the indentation level and style
        String prefix;
        if (indentationLevel == 0) {
            prefix = "";
        } else if (indentationLevel == 1) {
            prefix = counters[0] + ". ";
            counters[0]++;
            counters[1] = 1;
            counters[2] = 1;
            counters[3] = 1;
        } else if (indentationLevel == 2) {
            prefix = " " + (char) ('a' + counters[1] - 1) + ". ";
            counters[1]++;
            counters[2] = 1;
            counters[3] = 1;
        } else if (indentationLevel == 3) {
            prefix = "  " + (char) ('a' + counters[2] - 1) + ". ";
            counters[2]++;
            counters[3] = 1;
        } else if (indentationLevel == 4) {
//            String[] romanNumerals = {"   i. ", "  ii. ", " iii. ", "  iv. ", "   v. ", "  vi. ", " vii. ", "viii. ", "  ix. ", "   x. "};
            String s=IntegerToRoman.RomanNumerals(counters[3]);
            int l=IntegerToRoman.RomanNumerals(counters[3]).length();
            for(int i=0;i<4-l;i++){
                s =" "+ s;
            }
            prefix= s + ". ";
            counters[3]++;
        } else {
            prefix = "    • ";
        }

        // Append the prefix and the task name to the resultBuilder
        resultBuilder.append("  ".repeat(indentationLevel)).append(prefix).append(currentTaskName).append("\n");

        // Recursively process the children task lists
        List<TaskList> subTaskLists = taskList.getTaskLists();
        if (subTaskLists != null) {
            for (TaskList subTaskList : subTaskLists) {
                getHierarchicalTaskListStringWithStyleHelper(subTaskList, resultBuilder, indentationLevel + 1, counters);
            }
        }
    }
}