package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;


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

    @GetMapping("/")
    public String showAllUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "showAll";
    }

    @GetMapping("/user")
    public String showUser(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "show";
    }

    @GetMapping("/new")
    public String createUser(@ModelAttribute("user") User user) {
        return "new";
    }

    @PostMapping
    public String create(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "/new";
//        if (role != null) {
//            Set<Role> userRoles = role.stream()
//                    .map(roleName -> roleService.findByName(roleName)) // Найдите роль по имени
//                    .collect(Collectors.toSet());
//            user.setRoles(userRoles);
//        }
        userService.saveUser(user);

        return "redirect:/admin/";
    }

    @PatchMapping("/user/edit")
    public String update(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult,
                         @RequestParam(value = "id") Long id) {
        if (bindingResult.hasErrors())
            return "/show";
        userService.update(id, user);
        return "redirect:/admin/";
    }

    @DeleteMapping("/user/delete")
    public String delete(@RequestParam(value = "id") Long id) {
        userService.deleteUser(id);

        return "redirect:/admin/";
    }


}

