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

    @GetMapping("/")
    public String showAllUsers(Model model) {
        model.addAttribute("users", userServiceImpl.findAll());
        return "showAll";
    }

    @GetMapping("/user")
    public String showUser(Model model) {
        model.addAttribute("newUser", new User());
        model.addAttribute("allRoles", roleServiceImpl.findAll());
        return "show";
    }

    @GetMapping
    public String openPageForAuthenticatedAdmins(Model model, Principal principal) {
        model.addAttribute("user", userServiceImpl.findByName(principal.getName()));
        model.addAttribute("newUser", new User());
        model.addAttribute("allRoles", roleServiceImpl.findAll());
        return "pageForAdmins";
    }
//    @GetMapping("/user")
//    public String showUser(@RequestParam(value = "id") Long id, Model model) {
//        model.addAttribute("user", userServiceImpl.findById(id));
//        model.addAttribute("allRoles", roleServiceImpl.findAll());
//        return "show";
//    }

    @GetMapping("/new")
    public String createUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("allRoles", roleServiceImpl.findAll());
        return "new";
    }

    @PostMapping
    public String create(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors())
            return "/new";

        model.addAttribute("user", new User());
        userServiceImpl.saveUser(user);
        return "redirect:/user";
    }

    @PatchMapping("/user/edit")
    public String update(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult,
                         @RequestParam(value = "id") Long id) {
        if (bindingResult.hasErrors())
            return "/show";
        userServiceImpl.update(id, user);
        return "redirect:/admin/";
    }

    @DeleteMapping("/user/delete")
    public String delete(@RequestParam(value = "id") Long id) {
        userServiceImpl.deleteUserById(id);
        return "redirect:/admin/";
    }


}

