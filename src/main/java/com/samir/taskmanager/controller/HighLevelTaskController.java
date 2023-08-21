package com.samir.taskmanager.controller;

import com.samir.taskmanager.Utility.NullChecker;
import com.samir.taskmanager.entity.HighLevelTask;
import com.samir.taskmanager.entity.TaskTag;
import com.samir.taskmanager.service.HighLevelTaskService;
import com.samir.taskmanager.service.SisterService;
import com.samir.taskmanager.service.TaskListService;
import com.samir.taskmanager.service.TaskService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HighLevelTaskController {
    @Autowired
    private SisterService sisterService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HighLevelTaskService highLevelTaskService;
    @Autowired
    private TaskListService taskListService;

    @RequestMapping(value = "/highleveltasks/sort", method = {RequestMethod.GET, RequestMethod.POST})
    public String sortHlt(@RequestParam(required = false) Long id, Model model) {
        model.addAttribute("highleveltasks", highLevelTaskService.getAllHltTaskTaskTag(id != null ? id : 1L));
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
        return "redirect:/highleveltasks/sort";
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
        return "redirect:/highleveltasks/sort";
    }

    @GetMapping("/highleveltask/delete/{id}")
    public String deletehlt(@PathVariable Long id, HttpServletRequest request) {
        highLevelTaskService.deleteHighTaskById(id);

        String previousPage = request.getHeader("referer");
        if (previousPage == null || previousPage.isEmpty()) {
            // If the referer header is not available, redirect to a default page
            return "redirect:/highleveltasks/sort";
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
            return "redirect:/highleveltasks/sort";
        } else {
            // Redirect to the previous page
            return "redirect:" + previousPage;
        }
    }

    @GetMapping("/highleveltasks/{id}/view")
    public String view(Model model, @PathVariable Long id) {
        model.addAttribute("header", "Under High Level Task: " + highLevelTaskService.getHighLevelTaskById(id).getHighLevelTaskName());
        model.addAttribute("taskList", taskListService.getTreeLists(highLevelTaskService.getHighLevelTaskById(id)));
        model.addAttribute("tree", taskListService.getHierarchicalTaskListString(taskListService.getTreeLists(highLevelTaskService.getHighLevelTaskById(id))));
        model.addAttribute("tree1", taskListService.getHierarchicalTaskListStringWithStyle(taskListService.getTreeLists(highLevelTaskService.getHighLevelTaskById(id))));
        model.addAttribute("treeStyled", taskListService.getHirearchicalTaskListStringWithAnotherStyle(taskListService.getTreeLists(highLevelTaskService.getHighLevelTaskById(id))));
        return "tree/tree";
    }

    @RequestMapping(value = "/systems/{sid}/tasks/{tid}/highleveltasks/sort", method = {RequestMethod.GET, RequestMethod.POST})
    public String getAllHltUnderTask(@PathVariable("sid") Long sid, @PathVariable("tid") Long tid, @RequestParam(required = false) Long id, Model model) {
        model.addAttribute("sister", sisterService.getSisterById(sid));
        model.addAttribute("task", taskService.getTaskById(tid));
        model.addAttribute("hlt", highLevelTaskService.getAllHltTaskTagUnderATaskId(tid, id != null ? id : 1L));
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
        return "redirect:/systems/" + id + "/tasks/" + tid + "/highleveltasks/sort";
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
        return "redirect:/systems/" + id + "/tasks/" + tid + "/highleveltasks/sort";
    }

}
