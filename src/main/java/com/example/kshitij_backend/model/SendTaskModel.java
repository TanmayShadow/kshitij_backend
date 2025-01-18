package com.example.kshitij_backend.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Data
@Document(collection = "send_task")
public class SendTaskModel {
    @Id
    private String id;
    private String senderId;
    private List<String> recipientIds;
    private List<String> taskIds;
    private String sharedDate;

    public SendTaskModel(String id, String senderId, List<String> receiverId, List<String> taskId, String sharedDate) {
        this.id = id;
        this.senderId = senderId;
        this.recipientIds = receiverId;
        this.taskIds = taskId;
        this.sharedDate = sharedDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public List<String> getRecipientIds() {
        return recipientIds;
    }

    public void setRecipientIds(List<String> recipientIds) {
        this.recipientIds = recipientIds;
    }

    public List<String> getTaskIds() {
        return taskIds;
    }

    public void setTaskIds(List<String> taskIds) {
        this.taskIds = taskIds;
    }

    public String getSharedDate() {
        return sharedDate;
    }

    public void setSharedDate(String sharedDate) {
        this.sharedDate = sharedDate;
    }

    public SendTaskModel() {
    }
}
