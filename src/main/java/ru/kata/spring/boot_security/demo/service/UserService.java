package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    boolean saveUser(User user);

    void update(Long id, User user);

    User findById(Long id);

    List<User> findAll();

    User findByEmail(String email);

    boolean deleteUserById(Long id);
}
