package com.example.kshitij_backend.repository;

import com.example.kshitij_backend.model.TaskModel;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TaskRepo extends MongoRepository<TaskModel,String> {
    List<TaskModel> findByUid(String uid, Sort sort);
    TaskModel findByIdAndUid(String id,String uid);
}
