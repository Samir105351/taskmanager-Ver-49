package com.samir.taskmanager.controller;

import com.samir.taskmanager.service.TaskListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TaskListController {
    @Autowired
    private TaskListService taskListService;
    @GetMapping("/tree")
    public String treeThymeleaf(Model model){
        model.addAttribute("taskList",taskListService.getTaskLists());
        model.addAttribute("tree",taskListService.getHierarchicalTaskListString(taskListService.getTaskLists()));
        model.addAttribute("tree1",taskListService.getHierarchicalTaskListStringWithStyle(taskListService.getTaskLists()));
        model.addAttribute("treeStyled",taskListService.getHirearchicalTaskListStringWithAnotherStyle(taskListService.getTaskLists()));
        return "tree/tree";
    }
}
