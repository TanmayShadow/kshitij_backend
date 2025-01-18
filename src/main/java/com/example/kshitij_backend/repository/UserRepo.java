package com.example.kshitij_backend.repository;

import com.example.kshitij_backend.model.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepo extends MongoRepository<UserModel,String> {
    @Query("{ 'email': ?0, 'password': ?1 }")
    UserModel findByEmailAndPassword(String email, String password);
    UserModel findByEmail(String email);
}
