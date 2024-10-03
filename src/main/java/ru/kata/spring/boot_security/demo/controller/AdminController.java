package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.security.Principal;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String openPageForAdmins(Model model, Principal principal) {
        model.addAttribute("loginUser", userService.findByEmail(principal.getName()));
        model.addAttribute("users", userService.findAll());
        model.addAttribute("newUser", new User());
        model.addAttribute("allRoles", roleService.findAll());
        return "pageForAdmins";
    }

    @GetMapping("/")
    public String showAllUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "showAll";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("editUser") User editUser) {
        userService.update(editUser);
        return "/admin";
    }

    @GetMapping("/user/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        model.addAttribute("editUser", userService.findById(id));
        model.addAttribute("allRoles", roleService.findAll());
        return "pageForAdmins";
    }

    @PatchMapping("/user/edit/{id}")
    public String updateUser(@ModelAttribute("editUser") User user, @PathVariable Long id) {
        userService.update(id, user);
        return "redirect:/admin/users";
    }

    @PostMapping
    public String create(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "/new";
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }


}

