package com.samir.taskmanager.controller;

import com.samir.taskmanager.Utility.NullChecker;
import com.samir.taskmanager.entity.LowLevelSubTask;
import com.samir.taskmanager.entity.TaskTag;
import com.samir.taskmanager.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SubController {
    @Autowired
    private SisterService sisterService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HighLevelTaskService highLevelTaskService;
    @Autowired
    private LowLevelTaskService lowLevelTaskService;
    @Autowired
    private SubService subService;

    @RequestMapping(value = "/subtasks/sort", method = {RequestMethod.GET, RequestMethod.POST})
    public String sortHlt(@RequestParam(required = false) Long id, Model model) {
        model.addAttribute("subtasks", subService.getJoinSubLowTag(id != null ? id : 1L));
        return "sub/sub";
    }

    @GetMapping("/subtasks/new")
    public String createSubForm(Model model) {
        LowLevelSubTask lowLevelSubTask = new LowLevelSubTask();
        TaskTag taskTag = new TaskTag();
        model.addAttribute("sub", lowLevelSubTask);
        model.addAttribute("llts", lowLevelTaskService.getAllLowLevelTasks());
        return "sub/create_sub";
    }

    @PostMapping("/subtasks")
    public String savesub(@ModelAttribute("sub") LowLevelSubTask lowLevelSubTask) {
        TaskTag taskTag = new TaskTag();
        taskTag.setTaskTagName("L_" + lowLevelSubTask.getLowLevelTask().getLowLevelTaskID());
        if (NullChecker.check(lowLevelSubTask.getLowLevelSubTaskName()) == null) {
            taskTag.setTaskTagName(null);
        }
        lowLevelSubTask.setTaskTag(taskTag);
        subService.saveSubTask(lowLevelSubTask);
        return "redirect:/subtasks/sort";
    }

    @GetMapping("/subtasks/edit/{id}")
    public String editsubForm(@PathVariable Long id, Model model) {
        model.addAttribute("sub", subService.getSubTaskById(id));
        model.addAttribute("llts", lowLevelTaskService.getAllLowLevelTasks());
        return "sub/edit_sub";
    }

    @PostMapping("/subtasks/{id}")
    public String updatesub(@PathVariable Long id, @ModelAttribute("sub") LowLevelSubTask lowLevelSubTask, Model model) {
        LowLevelSubTask existingsub = subService.getSubTaskById(id);
        TaskTag existingtaskTag = existingsub.getTaskTag();
        existingsub.setLowLevelSubTaskName(lowLevelSubTask.getLowLevelSubTaskName());
        existingsub.setLowLevelTask(lowLevelSubTask.getLowLevelTask());
        existingtaskTag.setTaskTagName("L_" + lowLevelSubTask.getLowLevelTask().getLowLevelTaskID());
        subService.updateSubTask(existingsub);
        return "redirect:/subtasks/sort";
    }

    @GetMapping("/subtask/delete/{id}")
    public String deleteSub(@PathVariable Long id, HttpServletRequest request) {
        subService.deleteSubTaskById(id);

        String previousPage = request.getHeader("referer");
        if (previousPage == null || previousPage.isEmpty()) {
            // If the referer header is not available, redirect to a default page
            return "redirect:/subtasks/sort";
        } else {
            // Redirect to the previous page
            return "redirect:" + previousPage;
        }
    }

    @GetMapping("/subtasks/deleteall")
    public String deleteAllLlt(HttpServletRequest request) {
        subService.deleteAllLowLevelSubTasks();
        String previousPage = request.getHeader("referer");
        if (previousPage == null || previousPage.isEmpty()) {
            // If the referer header is not available, redirect to a default page
            return "redirect:/subtasks/sort";
        } else {
            // Redirect to the previous page
            return "redirect:" + previousPage;
        }
    }

    @RequestMapping(value = "/systems/{sid}/tasks/{tid}/highleveltasks/{hid}/lowleveltasks/{lid}/lowlevelsubtasks/sort", method = {RequestMethod.GET, RequestMethod.POST})
    public String getAllLltUnderHlt(@PathVariable("sid") Long sid, @PathVariable("tid") Long tid, @PathVariable("hid") Long hid, @PathVariable("lid") Long lid, @RequestParam(required = false) Long id, Model model) {
        model.addAttribute("sister", sisterService.getSisterById(sid));
        model.addAttribute("task", taskService.getTaskById(tid));
        model.addAttribute("hlt", highLevelTaskService.getHighLevelTaskById(hid));
        model.addAttribute("llt", lowLevelTaskService.getLowLevelTaskById(lid));
        model.addAttribute("sub", subService.getJoinSubTaskTagByALlt(lid, id != null ? id : 1L));
        return "sub/sub_list_by_llt_id";
    }

    @GetMapping("highleveltasks/{hid}/lowleveltasks/{lid}/lowlevelsubtasks")
    public String getRedirectLlt(@PathVariable Long hid, @PathVariable Long lid, Model model) {
        Long t = highLevelTaskService.getHighLevelTaskById(hid).getTask().getTaskID();
        Long s = taskService.getTaskById(t).getSister().getSisterId();
        return "redirect:/systems/" + s + "/tasks/" + t + "/highleveltasks/" + hid + "/lowleveltasks/" + lid + "/lowlevelsubtasks/sort";
    }

    @GetMapping("/systems/{id}/tasks/{tid}/highleveltasks/{hid}/lowleveltasks/{lid}/lowlevelsubtasks/new")
    public String createHltFormbyt(@PathVariable Long tid, @PathVariable Long id, @PathVariable Long hid, @PathVariable Long lid, Model model) {
        LowLevelSubTask lowLevelSubTask = new LowLevelSubTask();
        model.addAttribute("llt", lowLevelTaskService.getLowLevelTaskById(lid));
        model.addAttribute("hlt", highLevelTaskService.getHighLevelTaskById(hid));
        model.addAttribute("sister", sisterService.getSisterById(id));
        model.addAttribute("task", taskService.getTaskById(tid));
        model.addAttribute("sub", lowLevelSubTask);
        return "sub/create_sub_by_l";
    }

    @PostMapping("/systems/{id}/tasks/{tid}/highleveltasks/{hid}/lowleveltasks/{lid}/lowlevelsubtasks")
    public String savelltbyh(@ModelAttribute("sub") LowLevelSubTask lowLevelSubTask, @PathVariable Long id, @PathVariable Long tid, @PathVariable Long hid, @PathVariable Long lid) {
        lowLevelSubTask.setLowLevelTask(lowLevelTaskService.getLowLevelTaskById(lid));
        TaskTag taskTag = new TaskTag();
        taskTag.setTaskTagName("L_" + lowLevelSubTask.getLowLevelTask().getLowLevelTaskID());
        if (NullChecker.check(lowLevelSubTask.getLowLevelSubTaskName()) == null) {
            taskTag.setTaskTagName(null);
        }
        lowLevelSubTask.setTaskTag(taskTag);
        subService.saveSubTask(lowLevelSubTask);
        return "redirect:/systems/" + id + "/tasks/" + tid + "/highleveltasks/" + hid + "/lowleveltasks/" + lid + "/lowlevelsubtasks/sort";
    }

    @GetMapping("/systems/{id}/tasks/{tid}/highleveltasks/{hid}/lowleveltasks/{lid}/lowlevelsubtasks/edit/{sid}")
    public String editlltForm(@PathVariable Long id, @PathVariable Long tid, @PathVariable Long hid, @PathVariable Long lid, @PathVariable Long sid, Model model) {
        model.addAttribute("sister", sisterService.getSisterById(id));
        model.addAttribute("task", taskService.getTaskById(tid));
        model.addAttribute("hlt", highLevelTaskService.getHighLevelTaskById(hid));
        model.addAttribute("l", lowLevelTaskService.getLowLevelTaskById(lid));
        model.addAttribute("sub", subService.getSubTaskById(sid));
        model.addAttribute("llts", lowLevelTaskService.getAllLowLevelTasks());
        return "sub/edit_sub_l";
    }

    @PostMapping("/systems/{id}/tasks/{tid}/highleveltasks/{hid}/lowleveltasks/{lid}/lowlevelsubtasks/{sid}")
    public String updatehlt(@PathVariable Long id, @PathVariable Long tid, @PathVariable Long hid, @PathVariable Long lid, @PathVariable Long sid, @ModelAttribute("sub") LowLevelSubTask lowLevelSubTask, Model model) {
        LowLevelSubTask existingSub = subService.getSubTaskById(sid);
        TaskTag existingtaskTag = existingSub.getTaskTag();
        existingSub.setLowLevelSubTaskName(lowLevelSubTask.getLowLevelSubTaskName());
        existingSub.setLowLevelTask(lowLevelSubTask.getLowLevelTask());
        existingtaskTag.setTaskTagName("L_" + lowLevelSubTask.getLowLevelTask().getLowLevelTaskID());
        subService.updateSubTask(existingSub);
        return "redirect:/systems/" + id + "/tasks/" + tid + "/highleveltasks/" + hid + "/lowleveltasks/" + lid + "/lowlevelsubtasks/sort";
    }
}
