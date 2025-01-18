package com.example.kshitij_backend.repository;

import com.example.kshitij_backend.model.SendTaskModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SendTaskRepo extends MongoRepository<SendTaskModel, String> {
    List<SendTaskModel> findBySenderId(String senderId);
    List<SendTaskModel> findByRecipientIdsContains(String recipientId);
}
