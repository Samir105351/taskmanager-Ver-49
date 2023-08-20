package com.samir.taskmanager.controller;

import com.samir.taskmanager.service.TaskTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TaskTagController {
    @Autowired
    private TaskTagService taskTagService;

    @RequestMapping(value = "/tasktags/sort", method = {RequestMethod.GET, RequestMethod.POST})
    public String systemPage(@RequestParam(required = false) String sortBy, Model model) {
        model.addAttribute("tasktags", taskTagService.getAllTaskTags(sortBy != null ? sortBy : "taskTagID"));
        return "tasktag/tasktags";
    }
}
