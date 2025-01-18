package com.example.kshitij_backend.service;

import com.example.kshitij_backend.model.TaskModel;
import com.example.kshitij_backend.repository.TaskRepo;
import com.example.kshitij_backend.util.CommonMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    TaskRepo taskRepo;

    public String addTaskOfUser(TaskModel taskModel){
        try {
            TaskModel task = taskRepo.save(taskModel);
            task.setStatus("NEW");
            return CommonMessage.SUCCESS;
        }catch (Exception e){
            System.out.println("Error in adding task into database : "+e.getMessage());
            return CommonMessage.FAILED;
        }
    }

    public List<TaskModel> getAllTasksOfUser(String uid){
        return taskRepo.findByUid(uid, Sort.by(Sort.Direction.ASC, "dueDate"));
    }

    public TaskModel getTaskById(String id, String uid){
        return taskRepo.findByIdAndUid(id,uid);
    }

    public String completeTask(String id,String uid){
        TaskModel taskModel = getTaskById(id,uid);
        if(taskModel==null)
            return CommonMessage.NOT_FOUND;
        taskModel.setEndDate();
        taskModel.setStatus("COMPLETED");
        try{
            taskRepo.save(taskModel);
            return CommonMessage.SUCCESS;
        }catch (Exception e){
            return CommonMessage.NOT_UPDATED;
        }
    }

}
