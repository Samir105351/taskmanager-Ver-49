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

    @GetMapping("/tasks")
    public String taskPage(Model model) {
        model.addAttribute("tasks", taskService.getAllTaskSisterTaskTags(1l));
        return "task/tasks";
    }

    @GetMapping("/tasks/sorted")
    public String taskSorted(Model model) {
        model.addAttribute("tasks", taskService.getAllTaskSisterTaskTags(2l));
        return "task/tasks";
    }

    @GetMapping("/tasks/systemidsorted")
    public String taskSystemIdSorted(Model model) {
        model.addAttribute("tasks", taskService.getAllTaskSisterTaskTags(3l));
        return "task/tasks";
    }

    @GetMapping("/tasks/systemnamesorted")
    public String taskSystemNameSorted(Model model) {
        model.addAttribute("tasks", taskService.getAllTaskSisterTaskTags(4l));
        return "task/tasks";
    }

    @GetMapping("/tasks/tagidsorted")
    public String taskTagIdSorted(Model model) {
        model.addAttribute("tasks", taskService.getAllTaskSisterTaskTags(5l));
        return "task/tasks";
    }

    @GetMapping("/tasks/tagnamesorted")
    public String taskNameSorted(Model model) {
        model.addAttribute("tasks", taskService.getAllTaskSisterTaskTags(6l));
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
        return "redirect:/tasks";
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
        return "redirect:/tasks";
    }

    @GetMapping("/task/delete/{id}")
    public String deletetask(@PathVariable Long id, HttpServletRequest request) {
        taskService.deleteTaskById(id);

        String previousPage = request.getHeader("referer");
        if (previousPage == null || previousPage.isEmpty()) {
            // If the referer header is not available, redirect to a default page
            return "redirect:/tasks";
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
            return "redirect:/tasks";
        } else {
            // Redirect to the previous page
            return "redirect:" + previousPage;
        }
    }

    @GetMapping("/systems/{id}/tasks")
    public String getAllTasksUnderSystem(@PathVariable Long id, Model model) {
        model.addAttribute("tasks", taskService.getAllTaskTaskTagUnderASisterId(id, 1l));
        model.addAttribute("sister", sisterService.getSisterById(id));
        return "task/task_list_by_system_id";
    }

    @GetMapping("/systems/{id}/tasks/sorted")
    public String getAllTasksUnderSystemSortTN(@PathVariable Long id, Model model) {
        model.addAttribute("tasks", taskService.getAllTaskTaskTagUnderASisterId(id, 2l));
        model.addAttribute("sister", sisterService.getSisterById(id));
        return "task/task_list_by_system_id";
    }

    @GetMapping("/systems/{id}/tasks/sortedByTGID")
    public String getAllTasksUnderSystemSortTGID(@PathVariable Long id, Model model) {
        model.addAttribute("tasks", taskService.getAllTaskTaskTagUnderASisterId(id, 3l));
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
        return "redirect:/systems/" + id + "/tasks";
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
        return "redirect:/systems/" + sid + "/tasks";
    }
}
