package com.example.kshitij_backend.controller;

import com.example.kshitij_backend.annotation.ValidateJwtToken;
import com.example.kshitij_backend.model.TaskModel;
import com.example.kshitij_backend.service.TaskService;
import com.example.kshitij_backend.util.CommonMessage;
import com.example.kshitij_backend.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    TaskService taskService;

    @ValidateJwtToken
    @PostMapping("/addTask")
    public ResponseEntity<?> addTask(@RequestBody TaskModel taskModel, HttpServletRequest request){
        String jwt = request.getHeader("X-Authorization");
        String id = JwtUtil.getUserIdByToken(jwt);
        taskModel.setStatus("In Progress");
        if(taskModel.getTitle()==null || taskModel.getTitle().isEmpty()
            || taskModel.getDescription()==null || taskModel.getDescription().isEmpty()
            || taskModel.getDueDate()==null)
            return new ResponseEntity<>("message : input data missing",HttpStatus.BAD_REQUEST);
        if(id.equals(CommonMessage.FAILED))
            return new ResponseEntity<>("Invalid JWT Token", HttpStatus.UNAUTHORIZED);
        taskModel.setUid(id);
        String response = taskService.addTaskOfUser(taskModel);
        if(response.equals(CommonMessage.FAILED))
            return new ResponseEntity<>("Unable to add task", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @ValidateJwtToken
    @PostMapping("/getTasks")
    public ResponseEntity<?> getTasks(HttpServletRequest request){
        String jwt = request.getHeader("X-Authorization");
        String id = JwtUtil.getUserIdByToken(jwt);
        if(id.equals(CommonMessage.FAILED))
            return new ResponseEntity<>("Invalid JWT Token", HttpStatus.UNAUTHORIZED);
        List<TaskModel> taskList = taskService.getAllTasksOfUser(id);
        Map<String,Object> response = new HashMap<>();
        if(taskList==null || taskList.isEmpty())
        {
            response.put("success",false);
            response.put("message","Tasks not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        response.put("success",true);
        response.put("tasks",taskList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @ValidateJwtToken
    @PostMapping("/completeTask/{id}")
    public ResponseEntity<?> completeTask(@PathVariable("id") String id, HttpServletRequest request) {
        String jwt = request.getHeader("X-Authorization");
        String uid = JwtUtil.getUserIdByToken(jwt);
        String response = taskService.completeTask(id,uid);
        if(response.equals(CommonMessage.SUCCESS))
            return new ResponseEntity<>(response, HttpStatus.OK);
        else if(response.equals(CommonMessage.NOT_FOUND))
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(response, HttpStatus.NOT_MODIFIED);
    }
}
