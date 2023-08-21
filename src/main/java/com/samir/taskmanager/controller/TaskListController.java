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
        model.addAttribute("header","List Of All Projects");
        model.addAttribute("taskList",taskListService.getTreeLists());
        model.addAttribute("tree",taskListService.getHierarchicalTaskListString(taskListService.getTreeLists()));
        model.addAttribute("tree1",taskListService.getHierarchicalTaskListStringWithStyle(taskListService.getTreeLists()));
        model.addAttribute("treeStyled",taskListService.getHirearchicalTaskListStringWithAnotherStyle(taskListService.getTreeLists()));
        return "tree/tree";
    }
}
