package com.example.kshitij_backend.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@Data
@Document(collection = "tasks")
public class TaskModel {
    @Id
    private String id;
    private String uid;
    private String title;
    private String description;
    private String status;
    private Date startDate = new Date();
    private String endDate;
    private Date dueDate;

    public TaskModel() {
    }

    public TaskModel(String id, String uid, String title, String description, String status, Date startDate, String endDate, Date dueDate) {
        this.id = id;
        this.uid = uid;
        this.title = title;
        this.description = description;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dueDate = dueDate;
    }

    public TaskModel(String id, String uid, String title, String description, String status) {
        this.id = id;
        this.uid = uid;
        this.title = title;
        this.description = description;
        this.status = status;
    }


    public TaskModel(String id, String uid, String title, String description, String status, String endDate, Date startDate) {
        this.id = id;
        this.uid = uid;
        this.title = title;
        this.description = description;
        this.status = status;
        this.endDate = endDate;
        this.startDate = startDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setStartDate(){
        startDate = new Date();
    }

    public void setEndDate(){
        endDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
    }
}
