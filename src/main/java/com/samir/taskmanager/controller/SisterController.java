package com.samir.taskmanager.controller;

import com.samir.taskmanager.entity.Sister;
import com.samir.taskmanager.service.SisterService;
import com.samir.taskmanager.service.TaskListService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class SisterController {
    @Autowired
    private SisterService sisterService;
    @Autowired
    private TaskListService taskListService;

    @RequestMapping(value = "/systems/sort", method = {RequestMethod.GET, RequestMethod.POST})
    public String systemPage(@RequestParam(required = false) String sortBy, Model model) {
        model.addAttribute("sisters", sisterService.getAllSister(sortBy != null ? sortBy : "sisterId"));
        return "sister/sisters";
    }

    @GetMapping("/systems/new")
    public String createSystemForm(Model model) {
        Sister sister = new Sister();
        model.addAttribute("sister", sister);
        return "sister/create_sister";
    }

    @PostMapping("/systems")
    public String saveSystem(@ModelAttribute("sister") Sister sister) {
        sisterService.saveSister(sister);
        return "redirect:/systems/sort";
    }

    @GetMapping("/systems/edit/{id}")
    public String editSystemForm(@PathVariable Long id, Model model) {
        model.addAttribute("sister", sisterService.getSisterById(id));
        return "sister/edit_sister";
    }

    @PostMapping("/systems/{id}")
    public String updateSystem(@PathVariable Long id, @ModelAttribute("student") Sister sister, Model model) {
        Sister existingSister = sisterService.getSisterById(id);
        existingSister.setSisterName(sister.getSisterName());

        sisterService.updateSister(existingSister);
        return "redirect:/systems/sort";
    }

    @GetMapping("/system/delete/{id}")
    public String deleteSister(@PathVariable Long id, HttpServletRequest request) {
        sisterService.deleteSisterById(id);

        String previousPage = request.getHeader("referer");
        if (previousPage == null || previousPage.isEmpty()) {
            // If the referer header is not available, redirect to a default page
            return "redirect:/systems/sort";
        } else {
            // Redirect to the previous page
            return "redirect:" + previousPage;
        }
    }

    @GetMapping("/systems/deleteall")
    public String deleteStudent() {
        sisterService.deleteAllSisters();
        return "redirect:/systems/sort";
    }
    @GetMapping("/systems/{id}/view")
    public String view(Model model,@PathVariable Long id){
        model.addAttribute("taskList",taskListService.getTreeLists(sisterService.getSisterById(id)));
        model.addAttribute("tree",taskListService.getHierarchicalTaskListString(taskListService.getTreeLists(sisterService.getSisterById(id))));
        model.addAttribute("tree1",taskListService.getHierarchicalTaskListStringWithStyle(taskListService.getTreeLists(sisterService.getSisterById(id))));
        model.addAttribute("treeStyled",taskListService.getHirearchicalTaskListStringWithAnotherStyle(taskListService.getTreeLists(sisterService.getSisterById(id))));
        return "tree/tree";
    }
}
