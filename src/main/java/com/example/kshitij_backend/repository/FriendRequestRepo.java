package com.example.kshitij_backend.repository;

import com.example.kshitij_backend.model.FriendRequestModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRequestRepo extends MongoRepository<FriendRequestModel, String> {
    Optional<FriendRequestModel> findBySenderIdAndReceiverId(String senderId, String receiverId);
    List<FriendRequestModel> findByReceiverIdAndStatus(String receiverId, String status);
    List<FriendRequestModel> findBySenderIdAndStatus(String senderId, String status);
}
