package com.shopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shopping.model.entity.User;
import com.shopping.service.UserService;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String register(@RequestParam("username") String username,
                           @RequestParam("password") String password,
                           @RequestParam("email") String email,
                           @RequestParam("phonenumber") Integer phonenumber,
                           Model model) {
    	
        User user = userService.createUser(username, password, email, phonenumber );
        model.addAttribute("message", "User registered successfully!");
        return "login";  // 跳轉到登錄頁面
    }

}
