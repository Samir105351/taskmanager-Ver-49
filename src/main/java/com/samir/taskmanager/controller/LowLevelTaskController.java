package com.samir.taskmanager.controller;

import com.samir.taskmanager.Utility.NullChecker;
import com.samir.taskmanager.entity.LowLevelTask;
import com.samir.taskmanager.entity.TaskTag;
import com.samir.taskmanager.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LowLevelTaskController {
    @Autowired
    private SisterService sisterService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HighLevelTaskService highLevelTaskService;
    @Autowired
    private LowLevelTaskService lowLevelTaskService;

    @GetMapping("/lowleveltasks")
    public String lltPage(Model model) {
        model.addAttribute("lowleveltasks", lowLevelTaskService.getAllLltHltTaskTag(1l));
        return "llt/llt";
    }

    @GetMapping("/lowleveltasks/sorted")
    public String lltPageSortedByName(Model model) {
        model.addAttribute("lowleveltasks", lowLevelTaskService.getAllLltHltTaskTag(2l));
        return "llt/llt";
    }

    @GetMapping("/lowleveltasks/hltidsorted")
    public String lltPageSortedByHltID(Model model) {
        model.addAttribute("lowleveltasks", lowLevelTaskService.getAllLltHltTaskTag(3l));
        return "llt/llt";
    }

    @GetMapping("/lowleveltasks/hltnamesorted")
    public String lltPageSortedByHltName(Model model) {
        model.addAttribute("lowleveltasks", lowLevelTaskService.getAllLltHltTaskTag(4l));
        return "llt/llt";
    }

    @GetMapping("/lowleveltasks/tagidsorted")
    public String lltPageSortedByTagID(Model model) {
        model.addAttribute("lowleveltasks", lowLevelTaskService.getAllLltHltTaskTag(5l));
        return "llt/llt";
    }

    @GetMapping("/lowleveltasks/tagnamesorted")
    public String lltPageSortedByTagName(Model model) {
        model.addAttribute("lowleveltasks", lowLevelTaskService.getAllLltHltTaskTag(6l));
        return "llt/llt";
    }

    @GetMapping("/lowleveltasks/new")
    public String createlltForm(Model model) {
        LowLevelTask lowLevelTask = new LowLevelTask();
        TaskTag taskTag = new TaskTag();
        model.addAttribute("llt", lowLevelTask);
        model.addAttribute("hlts", highLevelTaskService.getAllHighLevelTasks());
        return "llt/create_llt";
    }

    @PostMapping("/lowleveltasks")
    public String savellt(@ModelAttribute("llt") LowLevelTask lowLevelTask) {
        TaskTag taskTag = new TaskTag();
        taskTag.setTaskTagName("H_" + lowLevelTask.getHighLevelTask().getHighLevelTaskID());
        if (NullChecker.check(lowLevelTask.getLowLevelTaskName()) == null) {
            lowLevelTask.getTaskTag().setTaskTagName(null);
        }
        lowLevelTask.setTaskTag(taskTag);
        lowLevelTaskService.saveLowLevelTask(lowLevelTask);
        return "redirect:/lowleveltasks";
    }

    @GetMapping("/lowleveltasks/edit/{id}")
    public String editlltForm(@PathVariable Long id, Model model) {
        model.addAttribute("llt", lowLevelTaskService.getLowLevelTaskById(id));
        model.addAttribute("hlts", highLevelTaskService.getAllHighLevelTasks());
        return "llt/edit_llt";
    }

    @GetMapping("/lowleveltask/delete/{id}")
    public String deleteLlt(@PathVariable Long id, HttpServletRequest request) {
        lowLevelTaskService.deleteLowLevelTaskByID(id);

        String previousPage = request.getHeader("referer");
        if (previousPage == null || previousPage.isEmpty()) {
            // If the referer header is not available, redirect to a default page
            return "redirect:/lowleveltasks";
        } else {
            // Redirect to the previous page
            return "redirect:" + previousPage;
        }
    }

    @GetMapping("/lowleveltasks/deleteall")
    public String deleteAllLlt(HttpServletRequest request) {
        lowLevelTaskService.deleteAllLowLevelTasks();
        String previousPage = request.getHeader("referer");
        if (previousPage == null || previousPage.isEmpty()) {
            // If the referer header is not available, redirect to a default page
            return "redirect:/lowleveltasks";
        } else {
            // Redirect to the previous page
            return "redirect:" + previousPage;
        }
    }

    @PostMapping("/lowleveltasks/{id}")
    public String updatellt(@PathVariable Long id, @ModelAttribute("llt") LowLevelTask lowLevelTask, Model model) {
        LowLevelTask existingLlt = lowLevelTaskService.getLowLevelTaskById(id);
        TaskTag existingtaskTag = existingLlt.getTaskTag();
        existingLlt.setLowLevelTaskName(lowLevelTask.getLowLevelTaskName());
        existingLlt.setHighLevelTask(lowLevelTask.getHighLevelTask());
        existingtaskTag.setTaskTagName("H_" + lowLevelTask.getHighLevelTask().getHighLevelTaskID());
        lowLevelTaskService.updateLowLevelTask(existingLlt);
        return "redirect:/lowleveltasks";
    }

    @GetMapping("/systems/{id}/tasks/{tid}/highleveltasks/{hid}/lowleveltasks")
    public String getAllLltUnderHlt(@PathVariable Long id, @PathVariable Long tid, @PathVariable Long hid, Model model) {
        model.addAttribute("sister", sisterService.getSisterById(id));
        model.addAttribute("task", taskService.getTaskById(tid));
        model.addAttribute("hlt", highLevelTaskService.getHighLevelTaskById(hid));
        model.addAttribute("llt", lowLevelTaskService.getAllLltTaskTagUnderAHlt(hid, 1l));
        return "llt/llt_list_by_hlt_id";
    }

    @GetMapping("/systems/{id}/tasks/{tid}/highleveltasks/{hid}/lowleveltasks/LLTNsorted")
    public String getAllLltUnderHltLLTNSorted(@PathVariable Long id, @PathVariable Long tid, @PathVariable Long hid, Model model) {
        model.addAttribute("sister", sisterService.getSisterById(id));
        model.addAttribute("task", taskService.getTaskById(tid));
        model.addAttribute("hlt", highLevelTaskService.getHighLevelTaskById(hid));
        model.addAttribute("llt", lowLevelTaskService.getAllLltTaskTagUnderAHlt(hid, 2l));
        return "llt/llt_list_by_hlt_id";
    }

    @GetMapping("/systems/{id}/tasks/{tid}/highleveltasks/{hid}/lowleveltasks/TGIDsorted")
    public String getAllLltUnderHltTGIDSorted(@PathVariable Long id, @PathVariable Long tid, @PathVariable Long hid, Model model) {
        model.addAttribute("sister", sisterService.getSisterById(id));
        model.addAttribute("task", taskService.getTaskById(tid));
        model.addAttribute("hlt", highLevelTaskService.getHighLevelTaskById(hid));
        model.addAttribute("llt", lowLevelTaskService.getAllLltTaskTagUnderAHlt(hid, 3l));
        return "llt/llt_list_by_hlt_id";
    }

    @GetMapping("/task/{tid}/highleveltasks/{hid}/lowleveltasks")
    public String getAllLltUnderRedirect(@PathVariable Long hid, @PathVariable Long tid, Model model) {
        Long s = taskService.getTaskById(tid).getSister().getSisterId();
        return "redirect:" + "/systems/" + s + "/tasks/" + tid + "/highleveltasks/" + hid + "/lowleveltasks";
    }

    @GetMapping("/systems/{id}/tasks/{tid}/highleveltasks/{hid}/lowleveltasks/new")
    public String createhltFormbyt(@PathVariable Long tid, @PathVariable Long id, @PathVariable Long hid, Model model) {
        LowLevelTask lowLevelTask = new LowLevelTask();
        model.addAttribute("llt", lowLevelTask);
        model.addAttribute("hlt", highLevelTaskService.getHighLevelTaskById(hid));
        model.addAttribute("sister", sisterService.getSisterById(id));
        model.addAttribute("task", taskService.getTaskById(tid));
        return "llt/create_llt_by_h";
    }

    @PostMapping("/systems/{id}/tasks/{tid}/highleveltasks/{hid}/lowleveltasks/")
    public String savelltbyh(@ModelAttribute("hlt") LowLevelTask lowLevelTask, @PathVariable Long id, @PathVariable Long tid, @PathVariable Long hid) {
        lowLevelTask.setHighLevelTask(highLevelTaskService.getHighLevelTaskById(hid));
        TaskTag taskTag = new TaskTag();
        taskTag.setTaskTagName("H_" + lowLevelTask.getHighLevelTask().getHighLevelTaskID());
        if (NullChecker.check(lowLevelTask.getLowLevelTaskName()) == null) {
            taskTag.setTaskTagName(null);
        }
        lowLevelTask.setTaskTag(taskTag);
        lowLevelTaskService.saveLowLevelTask(lowLevelTask);
        return "redirect:/systems/" + id + "/tasks/" + tid + "/highleveltasks/" + hid + "/lowleveltasks";
    }

    @GetMapping("/systems/{id}/tasks/{tid}/highleveltasks/{hid}/lowleveltasks/edit/{lid}")
    public String editlltForm(@PathVariable Long id, @PathVariable Long tid, @PathVariable Long hid, @PathVariable Long lid, Model model) {
        model.addAttribute("sister", sisterService.getSisterById(id));
        model.addAttribute("task", taskService.getTaskById(tid));
        model.addAttribute("h", highLevelTaskService.getHighLevelTaskById(hid));
        model.addAttribute("llt", lowLevelTaskService.getLowLevelTaskById(lid));
        model.addAttribute("hlts", highLevelTaskService.getAllHighLevelTasks());
        return "llt/edit_llt_h";
    }

    @PostMapping("/systems/{id}/tasks/{tid}/highleveltasks/{hid}/lowleveltasks/{lid}")
    public String updatehlt(@PathVariable Long id, @PathVariable Long tid, @PathVariable Long hid, @PathVariable Long lid, @ModelAttribute("hlt") LowLevelTask lowLevelTask, Model model) {
        LowLevelTask existingLlt = lowLevelTaskService.getLowLevelTaskById(lid);
        TaskTag existingtaskTag = existingLlt.getTaskTag();
        existingLlt.setLowLevelTaskName(lowLevelTask.getLowLevelTaskName());
        existingLlt.setHighLevelTask(lowLevelTask.getHighLevelTask());
        existingtaskTag.setTaskTagName("H_" + lowLevelTask.getHighLevelTask().getHighLevelTaskID());
        lowLevelTaskService.updateLowLevelTask(existingLlt);
        return "redirect:/systems/" + id + "/tasks/" + tid + "/highleveltasks/" + hid + "/lowleveltasks";
    }
}
