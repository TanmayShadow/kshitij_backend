package com.example.kshitij_backend.controller;

import com.example.kshitij_backend.model.UserModel;
import com.example.kshitij_backend.service.UserService;
import com.example.kshitij_backend.util.CommonMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserModel user) {
        if(user.getName()==null || user.getName().isEmpty()
            || user.getEmail()==null || user.getEmail().isEmpty()
            || user.getPassword()==null || user.getPassword().isEmpty())
            return new ResponseEntity<>("Message : Missing Details",HttpStatus.BAD_REQUEST);
        String status = userService.saveUser(user);
        return ResponseEntity.ok(status);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam String email, @RequestParam String password) {
        String jwt = userService.validateUser(email, password);
        if (jwt.equals(CommonMessage.FAILED)) {
            return new ResponseEntity<>("Invalid Email or Password", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(jwt,HttpStatus.OK);
    }
}
