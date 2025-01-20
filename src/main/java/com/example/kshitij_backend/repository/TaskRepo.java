package com.example.kshitij_backend.repository;

import com.example.kshitij_backend.model.TaskModel;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.kshitij_backend.model.StatusCount;

import java.util.List;

public interface TaskRepo extends MongoRepository<TaskModel,String> {
    List<TaskModel> findByUid(String uid, Sort sort);
    TaskModel findByIdAndUid(String id,String uid);

    @Aggregation(pipeline = {
            "{ $match: { uid: ?0, status: { $ne: null } } }", // Match tasks by user ID and exclude null status
            "{ $group: { _id: '$status', count: { $sum: 1 } } }", // Group by status and count
            "{ $project: { status: '$_id', count: 1, _id: 0 } }" // Rename _id to status and exclude _id
    })
    List<StatusCount> countTasksByStatus(String userId);


}
