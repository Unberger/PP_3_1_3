package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import javax.validation.Valid;
import java.security.Principal;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserServiceImpl userServiceImpl;
    private final RoleServiceImpl roleServiceImpl;

    @Autowired
    public AdminController(UserServiceImpl userServiceImpl, RoleServiceImpl roleServiceImpl) {
        this.userServiceImpl = userServiceImpl;
        this.roleServiceImpl = roleServiceImpl;
    }

    @GetMapping
    public String openPageForAdmins(Model model, Principal principal) {
        model.addAttribute("loginUser", userServiceImpl.findByEmail(principal.getName()));
        model.addAttribute("users", userServiceImpl.findAll());
        model.addAttribute("newUser", new User());
        model.addAttribute("allRoles", roleServiceImpl.findAll());
        return "pageForAdmins";
    }

    @GetMapping("/")
    public String showAllUsers(Model model) {
        model.addAttribute("users", userServiceImpl.findAll());
        return "showAll";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("editUser") User editUser) {
        userServiceImpl.update(editUser);
        return "redirect:/admin";
    }

    @GetMapping("/user/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        model.addAttribute("editUser", userServiceImpl.findById(id));
        model.addAttribute("allRoles", roleServiceImpl.findAll());
        return "pageForAdmins";
    }

    @PatchMapping("/user/edit/{id}")
    public String updateUser(@ModelAttribute("editUser") User user, @PathVariable Long id) {
        userServiceImpl.update(id, user);
        return "redirect:/admin/users";
    }

    @PostMapping
    public String create(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors())
            return "/new";

        model.addAttribute("user", new User());
        userServiceImpl.saveUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        userServiceImpl.deleteUserById(id);
        return "redirect:/admin";
    }


}

