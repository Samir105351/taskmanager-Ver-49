package com.samir.taskmanager.controller;

import com.samir.taskmanager.service.TaskTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TaskTagController {
    @Autowired
    private TaskTagService taskTagService;

    @GetMapping("/tasktags/sort/{sortBy}")
    public String systemPage(Model model, @PathVariable("sortBy") String sortBy) {
        model.addAttribute("tasktags",sortBy.equals("id")?taskTagService.getAllTaskTags("taskTagID"):taskTagService.getAllTaskTags("taskTagName"));
        return "tasktag/tasktags";
    }
}
