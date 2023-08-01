package com.samir.taskmanager.controller;

import com.samir.taskmanager.Utility.NullChecker;
import com.samir.taskmanager.entity.LowLevelSubTask;
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

    @GetMapping("/subtasks/sort/{sortBy}")
    public String lltPage(Model model,@PathVariable("sortBy") String sortBy) {
        model.addAttribute("subtasks",sortBy.equals("id")?subService.getJoinSubLowTag(1l):sortBy.equals("name")?subService.getJoinSubLowTag(2l):sortBy.equals("lltID")?subService.getJoinSubLowTag(3l):sortBy.equals("lltName")?subService.getJoinSubLowTag(4l):sortBy.equals("tagID")?subService.getJoinSubLowTag(5l):subService.getJoinSubLowTag(6l));
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
        return "redirect:/subtasks/sort/id";
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
        return "redirect:/subtasks/sort/id";
    }

    @GetMapping("/subtask/delete/{id}")
    public String deleteSub(@PathVariable Long id, HttpServletRequest request) {
        subService.deleteSubTaskById(id);

        String previousPage = request.getHeader("referer");
        if (previousPage == null || previousPage.isEmpty()) {
            // If the referer header is not available, redirect to a default page
            return "redirect:/subtasks/sort/id";
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
            return "redirect:/subtasks/sort/id";
        } else {
            // Redirect to the previous page
            return "redirect:" + previousPage;
        }
    }

    @GetMapping("/systems/{id}/tasks/{tid}/highleveltasks/{hid}/lowleveltasks/{lid}/lowlevelsubtasks")
    public String getAllLltUnderHlt(@PathVariable Long id, @PathVariable Long tid, @PathVariable Long hid, @PathVariable Long lid, Model model) {
        model.addAttribute("sister", sisterService.getSisterById(id));
        model.addAttribute("task", taskService.getTaskById(tid));
        model.addAttribute("hlt", highLevelTaskService.getHighLevelTaskById(hid));
        model.addAttribute("llt", lowLevelTaskService.getLowLevelTaskById(lid));
        model.addAttribute("sub", subService.getJoinSubTaskTagByALlt(lid, 1l));
        return "sub/sub_list_by_llt_id";
    }

    @GetMapping("/systems/{id}/tasks/{tid}/highleveltasks/{hid}/lowleveltasks/{lid}/lowlevelsubtasks/SUBNSorted")
    public String getAllLltUnderHltSUBNSorted(@PathVariable Long id, @PathVariable Long tid, @PathVariable Long hid, @PathVariable Long lid, Model model) {
        model.addAttribute("sister", sisterService.getSisterById(id));
        model.addAttribute("task", taskService.getTaskById(tid));
        model.addAttribute("hlt", highLevelTaskService.getHighLevelTaskById(hid));
        model.addAttribute("llt", lowLevelTaskService.getLowLevelTaskById(lid));
        model.addAttribute("sub", subService.getJoinSubTaskTagByALlt(lid, 2l));
        return "sub/sub_list_by_llt_id";
    }

    @GetMapping("/systems/{id}/tasks/{tid}/highleveltasks/{hid}/lowleveltasks/{lid}/lowlevelsubtasks/TGIDSorted")
    public String getAllLltUnderHltTGIDSorted(@PathVariable Long id, @PathVariable Long tid, @PathVariable Long hid, @PathVariable Long lid, Model model) {
        model.addAttribute("sister", sisterService.getSisterById(id));
        model.addAttribute("task", taskService.getTaskById(tid));
        model.addAttribute("hlt", highLevelTaskService.getHighLevelTaskById(hid));
        model.addAttribute("llt", lowLevelTaskService.getLowLevelTaskById(lid));
        model.addAttribute("sub", subService.getJoinSubTaskTagByALlt(lid, 3l));
        return "sub/sub_list_by_llt_id";
    }

    @GetMapping("highleveltasks/{hid}/lowleveltasks/{lid}/lowlevelsubtasks")
    public String getRedirectLlt(@PathVariable Long hid, @PathVariable Long lid, Model model) {
        Long t = highLevelTaskService.getHighLevelTaskById(hid).getTask().getTaskID();
        Long s = taskService.getTaskById(t).getSister().getSisterId();
        return "redirect:/systems/" + s + "/tasks/" + t + "/highleveltasks/" + hid + "/lowleveltasks/" + lid + "/lowlevelsubtasks";
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
        return "redirect:/systems/" + id + "/tasks/" + tid + "/highleveltasks/" + hid + "/lowleveltasks/" + lid + "/lowlevelsubtasks";
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
        LowLevelSubTask existingsub = subService.getSubTaskById(sid);
        TaskTag existingtaskTag = existingsub.getTaskTag();
        existingsub.setLowLevelSubTaskName(lowLevelSubTask.getLowLevelSubTaskName());
        existingsub.setLowLevelTask(lowLevelSubTask.getLowLevelTask());
        existingtaskTag.setTaskTagName("L_" + lowLevelSubTask.getLowLevelTask().getLowLevelTaskID());
        subService.updateSubTask(existingsub);
        return "redirect:/systems/" + id + "/tasks/" + tid + "/highleveltasks/" + hid + "/lowleveltasks/" + lid + "/lowlevelsubtasks";
    }
}
