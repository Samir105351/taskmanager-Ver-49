package com.samir.taskmanager.controller;

import com.samir.taskmanager.Utility.NullChecker;
import com.samir.taskmanager.entity.HighLevelTask;
import com.samir.taskmanager.entity.TaskTag;
import com.samir.taskmanager.service.HighLevelTaskService;
import com.samir.taskmanager.service.SisterService;
import com.samir.taskmanager.service.TaskService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HighLevelTaskController {
    @Autowired
    private SisterService sisterService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HighLevelTaskService highLevelTaskService;

    @GetMapping("/highleveltasks/sort/{sortBy}")
    public String hltPage(Model model,@PathVariable String sortBy) {
        model.addAttribute("highleveltasks", sortBy.equals("id")?highLevelTaskService.getAllHltTaskTaskTag(1l):sortBy.equals("name")?highLevelTaskService.getAllHltTaskTaskTag(2l):sortBy.equals("taskID")?highLevelTaskService.getAllHltTaskTaskTag(3l):sortBy.equals("taskName")?highLevelTaskService.getAllHltTaskTaskTag(4l):sortBy.equals("tagID")?highLevelTaskService.getAllHltTaskTaskTag(5l):highLevelTaskService.getAllHltTaskTaskTag(6l));
        return "hlt/hlt";
    }

    @GetMapping("/highleveltasks/new")
    public String createhltForm(Model model) {
        HighLevelTask highLevelTask = new HighLevelTask();
        model.addAttribute("hlt", highLevelTask);
        model.addAttribute("tasks", taskService.getAllTasks());
        return "hlt/create_hlt";
    }

    @PostMapping("/highleveltasks")
    public String savehlt(@ModelAttribute("hlt") HighLevelTask highLevelTask) {
        TaskTag taskTag = new TaskTag();
        taskTag.setTaskTagName("T_" + highLevelTask.getTask().getTaskID());
        if (NullChecker.check(highLevelTask.getHighLevelTaskName()) == null) {
            taskTag.setTaskTagName(null);
        }
        highLevelTask.setTaskTag(taskTag);
        highLevelTaskService.saveHighLevelTask(highLevelTask);
        return "redirect:/highleveltasks/sort/id";
    }

    @GetMapping("/highleveltasks/edit/{id}")
    public String edithltForm(@PathVariable Long id, Model model) {
        model.addAttribute("hlt", highLevelTaskService.getHighLevelTaskById(id));
        model.addAttribute("tasks", taskService.getAllTasks());
        return "hlt/edit_hlt";
    }

    @PostMapping("/highleveltasks/{id}")
    public String updatehlt(@PathVariable Long id, @ModelAttribute("hlt") HighLevelTask highLevelTask, Model model) {
        HighLevelTask existingHlt = highLevelTaskService.getHighLevelTaskById(id);
        TaskTag existingtaskTag = existingHlt.getTaskTag();
        existingHlt.setHighLevelTaskName(highLevelTask.getHighLevelTaskName());
        existingHlt.setTask(highLevelTask.getTask());
        existingtaskTag.setTaskTagName("T_" + highLevelTask.getTask().getTaskID());
        highLevelTaskService.updateHighLevelTask(existingHlt);
        return "redirect:/highleveltasks/sort/id";
    }

    @GetMapping("/highleveltask/delete/{id}")
    public String deletehlt(@PathVariable Long id, HttpServletRequest request) {
        highLevelTaskService.deleteHighTaskById(id);

        String previousPage = request.getHeader("referer");
        if (previousPage == null || previousPage.isEmpty()) {
            // If the referer header is not available, redirect to a default page
            return "redirect:/highleveltasks/sort/id";
        } else {
            // Redirect to the previous page
            return "redirect:" + previousPage;
        }
    }

    @GetMapping("/highleveltasks/deleteall")
    public String deleteallhlt(HttpServletRequest request) {
        highLevelTaskService.deleteAllHighLevelTasks();

        String previousPage = request.getHeader("referer");
        if (previousPage == null || previousPage.isEmpty()) {
            // If the referer header is not available, redirect to a default page
            return "redirect:/highleveltasks/sort/id";
        } else {
            // Redirect to the previous page
            return "redirect:" + previousPage;
        }
    }

    @GetMapping("/systems/{id}/tasks/{tid}/highleveltasks")
    public String getAllHltUnderTask(@PathVariable Long id, @PathVariable Long tid, Model model) {
        model.addAttribute("sister", sisterService.getSisterById(id));
        model.addAttribute("task", taskService.getTaskById(tid));
        model.addAttribute("hlt", highLevelTaskService.getAllHltTaskTagUnderATaskId(tid, 1l));
        return "hlt/hlt_list_by_task_id";
    }

    @GetMapping("/systems/{id}/tasks/{tid}/highleveltasks/sortHLTN")
    public String getAllHltUnderTaskSortedHLTN(@PathVariable Long id, @PathVariable Long tid, Model model) {
        model.addAttribute("sister", sisterService.getSisterById(id));
        model.addAttribute("task", taskService.getTaskById(tid));
        model.addAttribute("hlt", highLevelTaskService.getAllHltTaskTagUnderATaskId(tid, 2l));
        return "hlt/hlt_list_by_task_id";
    }

    @GetMapping("/systems/{id}/tasks/{tid}/highleveltasks/sortTGID")
    public String getAllHltUnderTaskSortedTGID(@PathVariable Long id, @PathVariable Long tid, Model model) {
        model.addAttribute("sister", sisterService.getSisterById(id));
        model.addAttribute("task", taskService.getTaskById(tid));
        model.addAttribute("hlt", highLevelTaskService.getAllHltTaskTagUnderATaskId(tid, 3l));
        return "hlt/hlt_list_by_task_id";
    }

    @GetMapping("/systems/{id}/tasks/{tid}/highleveltasks/new")
    public String createhltFormbyt(@PathVariable Long tid, @PathVariable Long id, Model model) {
        HighLevelTask highLevelTask = new HighLevelTask();
        model.addAttribute("hlt", highLevelTask);
        model.addAttribute("sister", sisterService.getSisterById(id));
        model.addAttribute("task", taskService.getTaskById(tid));
        return "hlt/create_hlt_by_t";
    }

    @PostMapping("/systems/{id}/tasks/{tid}/highleveltasks")
    public String savehltbyt(@ModelAttribute("hlt") HighLevelTask highLevelTask, @PathVariable Long id, @PathVariable Long tid) {
        highLevelTask.setTask(taskService.getTaskById(tid));
        TaskTag taskTag = new TaskTag();
        taskTag.setTaskTagName("T_" + highLevelTask.getTask().getTaskID());
        if (NullChecker.check(highLevelTask.getHighLevelTaskName()) == null) {
            taskTag.setTaskTagName(null);
        }
        highLevelTask.setTaskTag(taskTag);
        highLevelTaskService.saveHighLevelTask(highLevelTask);
        return "redirect:/systems/" + id + "/tasks/" + tid + "/highleveltasks";
    }

    @GetMapping("/systems/{id}/tasks/{tid}/highleveltasks/edit/{hid}")
    public String edithltForm(@PathVariable Long id, @PathVariable Long tid, @PathVariable Long hid, Model model) {
        model.addAttribute("sister", sisterService.getSisterById(id));
        model.addAttribute("t", taskService.getTaskById(tid));
        model.addAttribute("hlt", highLevelTaskService.getHighLevelTaskById(hid));
        model.addAttribute("tasks", taskService.getAllTasks());
        return "hlt/edit_hlt_t";
    }

    @PostMapping("/systems/{id}/tasks/{tid}/highleveltasks/{hid}")
    public String updatehlt(@PathVariable Long id, @PathVariable Long tid, @PathVariable Long hid, @ModelAttribute("hlt") HighLevelTask highLevelTask, Model model) {
        HighLevelTask existingHlt = highLevelTaskService.getHighLevelTaskById(hid);
        TaskTag existingtaskTag = existingHlt.getTaskTag();
        existingHlt.setHighLevelTaskName(highLevelTask.getHighLevelTaskName());
        existingHlt.setTask(highLevelTask.getTask());
        existingtaskTag.setTaskTagName("T_" + highLevelTask.getTask().getTaskID());
        highLevelTaskService.updateHighLevelTask(existingHlt);
        return "redirect:/systems/" + id + "/tasks/" + tid + "/highleveltasks";
    }

}
