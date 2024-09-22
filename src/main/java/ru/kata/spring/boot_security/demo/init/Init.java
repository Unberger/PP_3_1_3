package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


@Component
public class Init {

    private final UserServiceImpl userServiceImpl;
    private final RoleServiceImpl roleService;

    @Autowired
    public Init(UserServiceImpl userServiceImpl, RoleServiceImpl roleService) {
        this.userServiceImpl = userServiceImpl;
        this.roleService = roleService;
    }

    @PostConstruct
    public void start() {
        Role role1 = new Role("ROLE_ADMIN");
        Role role2 = new Role("ROLE_USER");

        roleService.saveRole(role1);
        roleService.saveRole(role2);

        List<Role> userRoles1 = new ArrayList<>();
        userRoles1.add(role1);
        userRoles1.add(role2);

        List<Role> userRoles2 = new ArrayList<>();
        userRoles2.add(role2);

        List<Role> userRoles3 = new ArrayList<>();
        userRoles3.add(role1);
//        userRoles3.add(role2);

        User user1 = new User();
        user1.setName("Nikita");
        user1.setLastName("Pastukhov");
        user1.setAge(23);
        user1.setEmail("abcddfddfdfds@ya.ru");
        user1.setPassword("100");
        user1.setRoles(userRoles1);

        User user2 = new User();
        user2.setName("Www");
        user2.setLastName("Pastukhova");
        user2.setAge(23);
        user2.setEmail("abcddfddfdfdsa@ya.ru");
        user2.setPassword("111");
        user2.setRoles(userRoles2);

        User user3 = new User();
        user3.setName("Qqq");
        user3.setLastName("Pastukhovewa");
        user3.setAge(11);
        user3.setEmail("qweqwesd@ya.ru");
        user3.setPassword("111");
        user3.setRoles(userRoles3);


        userServiceImpl.saveUser(user1);
        userServiceImpl.saveUser(user2);
        userServiceImpl.saveUser(user3);
    }
}
