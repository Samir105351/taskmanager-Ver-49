package com.samir.taskmanager.controller;

import com.samir.taskmanager.entity.Sister;
import com.samir.taskmanager.service.SisterService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class SisterController {
    @Autowired
    private SisterService sisterService;

    @GetMapping("/systems/sort/{sortBy}")
    public String systemPage(Model model,@PathVariable("sortBy") String sortBy) {
        model.addAttribute("sisters", sisterService.getAllSister(sortBy.equals("id") ? "sisterId" : "sisterName"));
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
        return "redirect:/systems/sort/id";
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
        return "redirect:/systems/sort/id";
    }

    @GetMapping("/system/delete/{id}")
    public String deleteSister(@PathVariable Long id, HttpServletRequest request) {
        sisterService.deleteSisterById(id);

        String previousPage = request.getHeader("referer");
        if (previousPage == null || previousPage.isEmpty()) {
            // If the referer header is not available, redirect to a default page
            return "redirect:/systems/sort/id";
        } else {
            // Redirect to the previous page
            return "redirect:" + previousPage;
        }
    }

    @GetMapping("/systems/deleteall")
    public String deleteStudent() {
        sisterService.deleteAllSisters();
        return "redirect:/systems/sort/id";
    }
}
