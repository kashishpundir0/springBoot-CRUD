package com.example.demo.services;

import com.example.demo.dtos.TaskDto;
import com.example.demo.entities.Employee;
import com.example.demo.entities.Task;
import com.example.demo.repositorise.EmployeeRepository;
import com.example.demo.repositorise.TasksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TasksRepository TasksRepository ;
    private final EmployeeRepository employeeRepository;

    public Task createTask(TaskDto request){
       Task Task=new Task();
       Employee employee=employeeRepository.findById(request.getId()).orElseThrow(()->new RuntimeException("Not Found"));
       List<Task> TaskList =employee.getTasks()!=null?employee.getTasks():new ArrayList<>();
       Task.setDescription(request.getDesc());
       Task= TasksRepository.save(Task);
       TaskList.add(Task);
       employee.setTasks(TaskList);
       employeeRepository.save(employee);
       return Task;
    }

    public List<Task> getAllTasks(){
       return TasksRepository.findAll();
    }

    public Task updateTasks(Long id, TaskDto updatedTask){
        Task Task = TasksRepository.findById((id)).orElseThrow(()-> new RuntimeException("Task not found"));
        Task.setDescription(updatedTask.getDesc());
        Task.setComplete(updatedTask.isComplete());
        return TasksRepository.save(Task);
    }

    public void deleteTask(Long id){
         TasksRepository.deleteById(id);
    }

}
