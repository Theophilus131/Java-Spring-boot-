package com.dem.controller;


import com.dem.model.User;
import com.dem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;



    @PostMapping
    public User addUser(@RequestBody User user) {
        userService.addUser(user);
        return user;
    }

    @GetMapping
    public List<User> getUsers() {
        userService.getUsers();
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        userService.getUser(id);
        return userService.getUser(id);
    }


    @DeleteMapping("/{id}")
    public String deleteUserById(@PathVariable int id) {
        userService.deleteUser(id);
        return "User deleted successfully";
    }

}
