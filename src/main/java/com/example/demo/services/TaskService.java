package com.example.demo.services;

import com.example.demo.dtos.TaskDto;
import com.example.demo.entities.Tasks;
import com.example.demo.repositorise.TasksRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
public class TaskService {
    private final TasksRepository tasksRepository ;

    public Tasks createTask(TaskDto request){
       Tasks task=new Tasks();
       task.setDesc(request.getDesc());
       return tasksRepository.save(task);
    }

    public List<Tasks> getAllTasks(){
       return tasksRepository.findAll();
    }

    public Tasks updateTasks(Long id, TaskDto updatedTask){
        Tasks task = tasksRepository.findById((id)).orElseThrow(()-> new RuntimeException("Task not found"));
        task.setDesc(updatedTask.getDesc());
        task.setComplete(updatedTask.isComplete());
        return tasksRepository.save(task);
    }

    public void deleteTask(Long id){
         tasksRepository.deleteById(id);
    }

}
