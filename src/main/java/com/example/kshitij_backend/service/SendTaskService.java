package com.example.kshitij_backend.service;

import com.example.kshitij_backend.model.SendTaskModel;
import com.example.kshitij_backend.repository.SendTaskRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SendTaskService {
    @Autowired
    private SendTaskRepo senderRepo;

    // Save a share record
    public SendTaskModel shareTasks(SendTaskModel sendTaskModel) {
        // Set the shared date if not already set
        if (sendTaskModel.getSharedDate() == null || sendTaskModel.getSharedDate().isEmpty()) {
            String currentDateTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
            sendTaskModel.setSharedDate(currentDateTime);
        }
        return senderRepo.save(sendTaskModel);
    }

    // Get all tasks shared by a specific user
    public List<SendTaskModel> getSharedTasksBySender(String senderId) {
        return senderRepo.findBySenderId(senderId);
    }

    // Get all tasks shared with a specific recipient
    public List<SendTaskModel> getSharedTasksForRecipient(String recipientId) {
        return senderRepo.findByRecipientIdsContains(recipientId);
    }

    // Get all share records
    public List<SendTaskModel> getAllSharedTasks() {
        return senderRepo.findAll();
    }
}
