package com.example.kshitij_backend.model;

public class FriendResponseDTO {
    private String name;
    private String uid;

    // Constructor
    public FriendResponseDTO(String name, String uid) {
        this.name = name;
        this.uid = uid;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
