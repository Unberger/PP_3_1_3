package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    void saveUser(User user);

    void update(User user);

    void update(Long id, User user);

    User findById(Long id);

    List<User> findAll();

    User findByEmail(String email);

    boolean deleteUserById(Long id);
}
