package com.example.kshitij_backend.controller;

import com.example.kshitij_backend.model.UserModel;
import com.example.kshitij_backend.service.UserService;
import com.example.kshitij_backend.util.CommonMessage;
import com.example.kshitij_backend.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserModel user) {
        if(user.getName()==null || user.getName().isEmpty()
            || user.getEmail()==null || user.getEmail().isEmpty()
            || user.getPassword()==null || user.getPassword().isEmpty())
            return new ResponseEntity<>("Message : Missing Details",HttpStatus.BAD_REQUEST);
        String status = userService.saveUser(user);
        Map<String,String> map = new HashMap<>();
        map.put("message",status);
        return ResponseEntity.ok(map);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam String email, @RequestParam String password) {
        String jwt = userService.validateUser(email, password);
        Map<String,Object> map = new HashMap<>();
        if (jwt.equals(CommonMessage.FAILED)) {
            map.put("message","Invalid Email or Password");
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        }
        map.put("token",jwt);
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    @PostMapping("/validateToken")
    public ResponseEntity<?> validateToken(HttpServletRequest request){
        String jwt = request.getHeader("X-Authorization");
        String id = JwtUtil.getUserIdByToken(jwt);
        Map<String,Object> map = new HashMap<>();

        if(id.equals(CommonMessage.FAILED))
        {
            map.put("success",false);
            map.put("message","Invalid JWT Token");
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        }
        map.put("success",true);
        map.put("message","Valid");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
