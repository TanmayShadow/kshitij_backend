package com.example.kshitij_backend.service;

import com.example.kshitij_backend.model.UserModel;
import com.example.kshitij_backend.repository.UserRepo;
import com.example.kshitij_backend.util.CommonMessage;
import com.example.kshitij_backend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    public String validateUser(String email, String password) {
//        password = passwordEncoder.encode(password);
        System.out.println("Encrypted Password : "+password);
        UserModel userModel = userRepo.findByEmail(email);
//        System.out.println("User ID "+userModel.getId());
        if(passwordEncoder.matches(password,userModel.getPassword()))
            return JwtUtil.generateJwtToken(userModel);

        return CommonMessage.FAILED;
    }

    public String saveUser(UserModel userModel) {
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
        if(userRepo.findByEmail(userModel.getEmail())==null)
        {
            userRepo.save(userModel);
            return "SUCCESS";
        }
        return "Email Already Exist";
    }
}
