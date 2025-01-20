package com.example.kshitij_backend.service;


import com.example.kshitij_backend.model.FriendRequestModel;
import com.example.kshitij_backend.model.UserModel;
import com.example.kshitij_backend.repository.FriendRequestRepo;
import com.example.kshitij_backend.repository.UserRepo;
import com.example.kshitij_backend.util.CommonMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FriendRequestService {

    @Autowired
    private FriendRequestRepo requestRepo;

    @Autowired
    private UserRepo userRepo;

    public FriendRequestModel sendFriendRequest(String senderId, String receiverId) {
        if (requestRepo.findBySenderIdAndReceiverId(senderId, receiverId).isPresent()) {
            return null;
        }

        FriendRequestModel friendRequest = new FriendRequestModel();
        friendRequest.setSenderId(senderId);
        friendRequest.setReceiverId(receiverId);
        friendRequest.setStatus("ACCEPTED");
        friendRequest.setCreatedAt(LocalDateTime.now());
        friendRequest.setUpdatedAt(LocalDateTime.now());
        try{
            return requestRepo.save(friendRequest);
        }catch (Exception e){
            System.out.println("Error : "+e.getMessage());
            return null;
        }
    }

    public List<FriendRequestModel> getPendingRequests(String receiverId) {
        return requestRepo.findByReceiverIdAndStatus(receiverId, "PENDING");
    }

    public FriendRequestModel respondToRequest(String requestId, String status) {
        FriendRequestModel friendRequest = requestRepo.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Friend request not found"));

        if (!status.equals("ACCEPTED") && !status.equals("DECLINED")) {
            throw new IllegalArgumentException("Invalid status");
        }

        friendRequest.setStatus(status);
        friendRequest.setUpdatedAt(LocalDateTime.now());
        return requestRepo.save(friendRequest);
    }

    public List<FriendRequestModel> getFriends(String userId) {
        return requestRepo.findBySenderIdAndStatus(userId, "ACCEPTED");
    }

    public List<UserModel> getAllUsers() {
        return userRepo.findAll();
    }


}
