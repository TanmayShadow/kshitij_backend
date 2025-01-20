package com.example.kshitij_backend.controller;

import com.example.kshitij_backend.annotation.ValidateJwtToken;
import com.example.kshitij_backend.model.FriendRequestModel;
import com.example.kshitij_backend.model.FriendResponseDTO;
import com.example.kshitij_backend.model.UserModel;
import com.example.kshitij_backend.service.FriendRequestService;
import com.example.kshitij_backend.service.UserService;
import com.example.kshitij_backend.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friends")
public class FriendRequestController {

    @Autowired
    private UserService userService;
    @Autowired
    private FriendRequestService friendRequestService;

    @ValidateJwtToken
    @PostMapping("/send")
    public ResponseEntity<?> sendFriendRequest(@RequestParam String senderId, @RequestParam String receiverId) {
        FriendRequestModel model =  friendRequestService.sendFriendRequest(senderId, receiverId);
        if(model==null)
            return new ResponseEntity<>("Friend request already sent", HttpStatus.OK);
        return  new ResponseEntity<>(model, HttpStatus.OK);
    }

    @ValidateJwtToken
    @GetMapping("/pending")
    public List<FriendRequestModel> getPendingRequests(@RequestParam String receiverId) {
        return friendRequestService.getPendingRequests(receiverId);
    }

    @ValidateJwtToken
    @PostMapping("/respond")
    public FriendRequestModel respondToRequest(@RequestParam String requestId, @RequestParam String status) {
        return friendRequestService.respondToRequest(requestId, status);
    }

    @ValidateJwtToken
    @GetMapping("/friends")
    public List<FriendResponseDTO> getFriends(HttpServletRequest request) {
        String jwt = request.getHeader("X-Authorization");
        String id = JwtUtil.getUserIdByToken(jwt);
        return  userService.getAllUsersByList(friendRequestService.getFriends(id));
    }

    @ValidateJwtToken
    @GetMapping("/allUsers")
    public List<UserModel> getAllUsers() {
        return friendRequestService.getAllUsers();
    }

}
