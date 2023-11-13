package com.company.Inventaryapp.controllers;

import com.company.Inventaryapp.exceptions.MiException;
import com.company.Inventaryapp.models.User;
import com.company.Inventaryapp.services.UserService;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/")
public class PortalController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @GetMapping("/register")
    public String register() {
        return "register.html";
    }

    @PostMapping("/register")
    public String registration(@RequestParam String name, @RequestParam String email, @RequestParam String password,
            String password2, ModelMap model, MultipartFile file) {

        try {
            userService.register(file, name, email, password, password2);

            model.put("success", "User registered successfully!");

            return "index.html";
        } catch (MiException ex) {

            model.put("error", ex.getMessage());
            model.put("name", name);
            model.put("email", email);

            return "register.html";
        }
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap model) {
        if (error != null) {
            model.put("error", "Invalid username or password!");
        }

        return "login.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/home")
    public String home(HttpSession session) {
        User loggedInUser = (User) session.getAttribute("userSession");

        if (loggedInUser.getRol().toString().equals("ADMIN")) {
            return "redirect:/admin/dashboard";
        }

        return "home.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/profile")
    public String profile(ModelMap model, HttpSession session) {
        User user = (User) session.getAttribute("userSession");
        model.put("user", user);
        return "user_update.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PostMapping("/profile/{id}")
    public String updateProfile(MultipartFile file, @PathVariable String id, @RequestParam String name,
            @RequestParam String email, @RequestParam String password,
            @RequestParam String password2, ModelMap model) {

        try {
            userService.update(file, id, name, email, password, password2);

            model.put("success", "User updated successfully!");

            return "home.html";
        } catch (MiException ex) {

            model.put("error", ex.getMessage());
            model.put("name", name);
            model.put("email", email);

            return "user_update.html";
        }
    }
}
