package com.benson.mediaapi.controller;

import com.benson.mediaapi.model.User;
import com.benson.mediaapi.service.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        boolean result = userService.register(user);
        if (!result){
            return ResponseEntity.badRequest().body("Fail");
        }
        return ResponseEntity.ok("User registered successfully");
    }
}
