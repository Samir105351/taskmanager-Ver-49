package com.samir.taskmanager.controller;

import com.samir.taskmanager.Utility.NullChecker;
import com.samir.taskmanager.entity.Task;
import com.samir.taskmanager.entity.TaskTag;
import com.samir.taskmanager.service.SisterService;
import com.samir.taskmanager.service.TaskService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TaskController {
    @Autowired
    private SisterService sisterService;
    @Autowired
    private TaskService taskService;

    @RequestMapping(value = "/tasks/sort", method = {RequestMethod.GET, RequestMethod.POST})
    public String sortTasks(@RequestParam(required = false) Long id, Model model) {
        model.addAttribute("tasks", taskService.getAllTaskSisterTaskTags(id != null ? id : 1L));
        return "task/tasks";
    }

    @GetMapping("/tasks/new")
    public String createTaskForm(Model model) {
        Task task = new Task();
        model.addAttribute("task", task);
        model.addAttribute("sisters", sisterService.getAllSister("sisterId"));
        return "task/create_tasks";
    }

    @PostMapping("/tasks")
    public String saveTask(@ModelAttribute("task") Task task) {
        TaskTag taskTag = new TaskTag();
        taskTag.setTaskTagName("S_" + task.getSister().getSisterId());
        if (NullChecker.check(task.getTaskName()) == null) {
            taskTag.setTaskTagName(null);
        }
        task.setTaskTag(taskTag);
        taskService.saveTask(task);
        return "redirect:/tasks/sort";
    }

    @GetMapping("/tasks/edit/{id}")
    public String editTaskForm(@PathVariable Long id, Model model) {
        model.addAttribute("task", taskService.getTaskById(id));
        model.addAttribute("sisters", sisterService.getAllSister("sisterId"));
        return "task/edit_task";
    }

    @PostMapping("/tasks/{id}")
    public String updateTask(@PathVariable Long id, @ModelAttribute("task") Task task, Model model) {
        Task existingtask = taskService.getTaskById(id);
        TaskTag existingtaskTag = existingtask.getTaskTag();
        existingtask.setTaskName(task.getTaskName());
        existingtask.setSister(task.getSister());
        existingtaskTag.setTaskTagName("S_" + task.getSister().getSisterId());
        taskService.updateTask(existingtask);
        return "redirect:/tasks/sort";
    }

    @GetMapping("/task/delete/{id}")
    public String deleteTask(@PathVariable Long id, HttpServletRequest request) {
        taskService.deleteTaskById(id);

        String previousPage = request.getHeader("referer");
        if (previousPage == null || previousPage.isEmpty()) {
            // If the referer header is not available, redirect to a default page
            return "redirect:/tasks/sort";
        } else {
            // Redirect to the previous page
            return "redirect:" + previousPage;
        }
    }

    @GetMapping("/tasks/deleteall")
    public String deleteAllTask(HttpServletRequest request) {
        taskService.deleteAllTasks();

        String previousPage = request.getHeader("referer");
        if (previousPage == null || previousPage.isEmpty()) {
            // If the referer header is not available, redirect to a default page
            return "redirect:/tasks/sort";
        } else {
            // Redirect to the previous page
            return "redirect:" + previousPage;
        }
    }

    @RequestMapping(value = "/systems/{sid}/tasks/sort", method = {RequestMethod.GET, RequestMethod.POST})
    public String getAllTasksUnderSystems(@PathVariable("sid") Long sid,@RequestParam(required = false) Long id, Model model) {
        model.addAttribute("tasks", taskService.getAllTaskTaskTagUnderASisterId(sid,id != null ? id : 1L));
        model.addAttribute("sister", sisterService.getSisterById(sid));
        return "task/task_list_by_system_id";
    }

    @GetMapping("/systems/{id}/tasks/new")
    public String createTasksUnderSystem(@PathVariable Long id, Model model) {
        Task task = new Task();
        model.addAttribute("task", task);
        model.addAttribute("sister", sisterService.getSisterById(id));
        return "task/create_task_by_sid";
    }

    @PostMapping("/systems/{id}/tasks")
    public String saveTaskUnderSystem(@PathVariable Long id, @ModelAttribute("task") Task task) {
        task.setSister(sisterService.getSisterById(id));
        TaskTag taskTag = new TaskTag();
        taskTag.setTaskTagName("S_" + task.getSister().getSisterId());
        if (NullChecker.check(task.getTaskName()) == null) {
            taskTag.setTaskTagName(null);
        }
        task.setTaskTag(taskTag);
        taskService.saveTask(task);
        return "redirect:/systems/" + id + "/tasks/sort";
    }

    @GetMapping("/systems/{sid}/tasks/edit/{id}")
    public String editTaskForm(@PathVariable Long id, @PathVariable Long sid, Model model) {
        model.addAttribute("task", taskService.getTaskById(id));
        model.addAttribute("s", sisterService.getSisterById(sid));
        model.addAttribute("sisters", sisterService.getAllSister("sisterId"));
        return "task/edit_task_s";
    }

    @PostMapping("system/{sid}/tasks/{id}")
    public String updateTask(@PathVariable Long id, @PathVariable Long sid, @ModelAttribute("task") Task task, Model model) {
        Task existingtask = taskService.getTaskById(id);
        TaskTag existingtaskTag = existingtask.getTaskTag();
        existingtask.setTaskName(task.getTaskName());
        existingtask.setSister(task.getSister());
        existingtaskTag.setTaskTagName("S_" + task.getSister().getSisterId());
        taskService.updateTask(existingtask);
        return "redirect:/systems/" + sid + "/tasks/sort";
    }
}

