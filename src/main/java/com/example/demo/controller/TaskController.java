package com.example.demo.controller;

import com.example.demo.dtos.TaskDto;
import com.example.demo.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllTask(@ModelAttribute TaskDto request){
        return ResponseEntity.ok(taskService.getAllTasks());
    }
    @PutMapping("/update")
    public ResponseEntity<?> updateTasks(@PathVariable Long id, @RequestBody TaskDto updateTask){
        return ResponseEntity.ok(taskService.updateTasks(id,updateTask));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        taskService.deleteTask(id);
        return ResponseEntity.ok("Task deleted");
    }

}
