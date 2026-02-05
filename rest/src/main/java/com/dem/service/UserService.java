package com.dem.service;

import com.dem.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private List<User> users = new ArrayList<User>();

    public List<User> getUsers() {
        return users;
    }

    public User getUser(int id) {
        users.get(id);
        return users.get(id);
    }


    public User addUser(User user) {
        users.add(user);
        return user;
    }


    public void deleteUser(int id) {
        users.remove(id);
    }



}
