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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TaskController {
    @Autowired
    private SisterService sisterService;
    @Autowired
    private TaskService taskService;

    @GetMapping("/tasks/sort/{sortBy}")
    public String taskPage(Model model,@PathVariable("sortBy") String sortBy) {
        model.addAttribute("tasks",sortBy.equals("id")?taskService.getAllTaskSisterTaskTags(1l):sortBy.equals("name")?taskService.getAllTaskSisterTaskTags(2l):sortBy.equals("sysId")?taskService.getAllTaskSisterTaskTags(3l):sortBy.equals("sysName")?taskService.getAllTaskSisterTaskTags(4l):sortBy.equals("tagID")?taskService.getAllTaskSisterTaskTags(5l):taskService.getAllTaskSisterTaskTags(6l));
        return "task/tasks";
    }

    @GetMapping("/tasks/new")
    public String createtaskForm(Model model) {
        Task task = new Task();
        model.addAttribute("task", task);
        model.addAttribute("sisters", sisterService.getAllSister("sisterId"));
        return "task/create_tasks";
    }

    @PostMapping("/tasks")
    public String savetask(@ModelAttribute("task") Task task) {
        TaskTag taskTag = new TaskTag();
        taskTag.setTaskTagName("S_" + task.getSister().getSisterId());
        if (NullChecker.check(task.getTaskName()) == null) {
            taskTag.setTaskTagName(null);
        }
        task.setTaskTag(taskTag);
        taskService.saveTask(task);
        return "redirect:/tasks/sort/id";
    }

    @GetMapping("/tasks/edit/{id}")
    public String edittaskForm(@PathVariable Long id, Model model) {
        model.addAttribute("task", taskService.getTaskById(id));
        model.addAttribute("sisters", sisterService.getAllSister("sisterId"));
        return "task/edit_task";
    }

    @PostMapping("/tasks/{id}")
    public String updatetask(@PathVariable Long id, @ModelAttribute("task") Task task, Model model) {
        Task existingtask = taskService.getTaskById(id);
        TaskTag existingtaskTag = existingtask.getTaskTag();
        existingtask.setTaskName(task.getTaskName());
        existingtask.setSister(task.getSister());
        existingtaskTag.setTaskTagName("S_" + task.getSister().getSisterId());
        taskService.updateTask(existingtask);
        return "redirect:/tasks/sort/id";
    }

    @GetMapping("/task/delete/{id}")
    public String deletetask(@PathVariable Long id, HttpServletRequest request) {
        taskService.deleteTaskById(id);

        String previousPage = request.getHeader("referer");
        if (previousPage == null || previousPage.isEmpty()) {
            // If the referer header is not available, redirect to a default page
            return "redirect:/tasks/sort/id";
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
            return "redirect:/tasks/sort/id";
        } else {
            // Redirect to the previous page
            return "redirect:" + previousPage;
        }
    }

    @GetMapping("/systems/{id}/tasks/sort/{sortBy}")
    public String getAllTasksUnderSystem(@PathVariable("id") Long id,@PathVariable("sortBy") String sortBy, Model model) {
        model.addAttribute("tasks", sortBy.equals("id")?taskService.getAllTaskTaskTagUnderASisterId(id, 1l):sortBy.equals("name")?taskService.getAllTaskTaskTagUnderASisterId(id, 2l):taskService.getAllTaskTaskTagUnderASisterId(id, 3l));
        model.addAttribute("sister", sisterService.getSisterById(id));
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
        return "redirect:/systems/" + id + "/tasks/sort/id";
    }

    @GetMapping("/systems/{sid}/tasks/edit/{id}")
    public String edittaskForm(@PathVariable Long id, @PathVariable Long sid, Model model) {
        model.addAttribute("task", taskService.getTaskById(id));
        model.addAttribute("s", sisterService.getSisterById(sid));
        model.addAttribute("sisters", sisterService.getAllSister("sisterId"));
        return "task/edit_task_s";
    }

    @PostMapping("system/{sid}/tasks/{id}")
    public String updatetask(@PathVariable Long id, @PathVariable Long sid, @ModelAttribute("task") Task task, Model model) {
        Task existingtask = taskService.getTaskById(id);
        TaskTag existingtaskTag = existingtask.getTaskTag();
        existingtask.setTaskName(task.getTaskName());
        existingtask.setSister(task.getSister());
        existingtaskTag.setTaskTagName("S_" + task.getSister().getSisterId());
        taskService.updateTask(existingtask);
        return "redirect:/systems/" + sid + "/tasks/sort/id";
    }
}

