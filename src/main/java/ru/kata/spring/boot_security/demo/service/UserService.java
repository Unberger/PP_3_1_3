package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    public boolean saveUser(User user);

    public void update(Long id, User user);

    public User findByName(String name);

    public boolean deleteUserById(Long id);

    public User findById(Long id);

    public List<User> findAll();
}
