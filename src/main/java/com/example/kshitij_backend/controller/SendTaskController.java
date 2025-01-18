package com.example.kshitij_backend.controller;

import com.example.kshitij_backend.annotation.ValidateJwtToken;
import com.example.kshitij_backend.model.SendTaskModel;
import com.example.kshitij_backend.service.SendTaskService;
import com.example.kshitij_backend.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shares")
public class SendTaskController {
    @Autowired
    private SendTaskService senderService;

    // API to share tasks
    @ValidateJwtToken
    @PostMapping("/share-tasks")
    public ResponseEntity<SendTaskModel> shareTasks(@RequestBody SendTaskModel senderModel, HttpServletRequest request) {
        String jwt = request.getHeader("X-Authorization");
        String id = JwtUtil.getUserIdByToken(jwt);
        senderModel.setSenderId(id);
        SendTaskModel savedRecord = senderService.shareTasks(senderModel);
        return ResponseEntity.status(201).body(savedRecord);
    }

    // API to get all tasks shared by a specific sender
    @GetMapping("/by-sender/{senderId}")
    public ResponseEntity<List<SendTaskModel>> getSharedTasksBySender(@PathVariable String senderId) {
        return ResponseEntity.ok(senderService.getSharedTasksBySender(senderId));
    }

    // API to get all tasks shared with a specific recipient
    @GetMapping("/for-recipient/{recipientId}")
    public ResponseEntity<List<SendTaskModel>> getSharedTasksForRecipient(@PathVariable String recipientId) {
        return ResponseEntity.ok(senderService.getSharedTasksForRecipient(recipientId));
    }
}
