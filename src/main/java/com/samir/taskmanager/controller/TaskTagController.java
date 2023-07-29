package com.samir.taskmanager.controller;

import com.samir.taskmanager.service.TaskTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TaskTagController {
    @Autowired
    private TaskTagService taskTagService;

    @GetMapping("/tasktags")
    public String systemPage(Model model) {
        model.addAttribute("tasktags", taskTagService.getAllTaskTags("taskTagID"));
        return "tasktag/tasktags";
    }

    @GetMapping("/tasktags/sorted")
    public String systemSorted(Model model) {
        model.addAttribute("tasktags", taskTagService.getAllTaskTags("taskTagName"));
        return "tasktag/tasktags";
    }
}
