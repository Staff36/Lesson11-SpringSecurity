package com.example.lesson11springsecurity.controllers;

import com.example.lesson11springsecurity.entities.User;
import com.example.lesson11springsecurity.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/app/score")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/get/current")
    public String getUsers(Principal principal) {
        String result = "Your score is " + userService.findByLogin(principal.getName()).getScore();
        return result;
    }

    @GetMapping("/inc")
    public String incrementYourScore(Principal principal) {
        User user = userService.findByLogin(principal.getName());
        userService.incrementUsersScore(user.getId());
        return "Success";
    }

    @GetMapping("/dec")
    public String decrementYourScore(Principal principal) {
        User user = userService.findByLogin(principal.getName());
        userService.decrementUsersScore(user.getId());
        return "Success";
    }

    @GetMapping("/get/{id}")
    public String decrementYourScore(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return String.format("User %s with id %d has %d score(s)", user.getLogin(), user.getId(), user.getScore());
    }

}
