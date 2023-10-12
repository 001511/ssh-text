package com.example.demo.service;


import com.example.demo.entity.User;

public interface UserService {

    void register(User user);

    User login(String user_name, String password);
}
