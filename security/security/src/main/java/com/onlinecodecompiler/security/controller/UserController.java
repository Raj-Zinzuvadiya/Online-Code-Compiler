package com.onlinecodecompiler.security.controller;


import com.onlinecodecompiler.security.model.User;
import com.onlinecodecompiler.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {


    @Autowired
    private UserService userService;



    @PostMapping("/register")
    public User register(@RequestBody User user)
    {
        return userService.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user)
    {
        System.out.println("Here");
        return "Here"; //userService.verify(user);
    }
}
