
package com.company.Inventaryapp.controllers;


import com.company.Inventaryapp.models.User;
import com.company.Inventaryapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    public String adminDashboard() {
        return "admin_dashboard.html";
    }

    @GetMapping("/users")
    public String listUsers(ModelMap model) {
        List<User> users = userService.ListUsers();
        model.addAttribute("users", users);

        return "admin_user_list";
    }

    @GetMapping("/changeRole/{id}")
    public String changeUserRole(@PathVariable String id) {
        userService.changeRol(id);

        return "redirect:/admin/users";
    }
}