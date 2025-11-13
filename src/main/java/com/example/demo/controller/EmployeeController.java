package com.example.demo.controller;

import com.example.demo.dtos.EmployeeDto;
import com.example.demo.dtos.TaskDto;
import com.example.demo.entities.Employee;
import com.example.demo.repositorise.EmployeeRepository;
import com.example.demo.services.EmployeeService;
import com.example.demo.services.LocationService;
import com.example.demo.services.TaskService;
import com.example.demo.utility.JwtTOkenUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
    private final TaskService taskService;
    private final JwtTOkenUtil jwtTOkenUtil;
    public final EmployeeRepository employeeRepository;
    private final LocationService locationService;

    @DeleteMapping("/delete/{Id}") // to delete the data
     public ResponseEntity<?> deleteUser(@PathVariable Long Id){

         employeeService.deleteEmployee(Id);
        return ResponseEntity.ok("Employee Deleted");
    }

    @PostMapping("/create") // used to create new data
    public ResponseEntity<?> createUser(@ModelAttribute EmployeeDto request){
        return ResponseEntity.ok(employeeService.create(request));
    }

    @GetMapping("/login")
    public ResponseEntity<?> getUsernameFromToken(@RequestHeader ("Authorization") String authHeader){
     String token = authHeader.substring(7);

     String email = jwtTOkenUtil.extractUsername(token);

     Employee employee = employeeRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("User not found"));
     return ResponseEntity.of(Optional.of(Map.of("username is : ", employee.getName())));
    }
    @PostMapping("/login")
    public ResponseEntity<?> updateToken(@RequestHeader ("Authorization") String authHeader){
        String token = authHeader.substring(7);
        String email = jwtTOkenUtil.extractUsername(token);
        Employee employee = employeeRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("user not found"));

        String newToken = jwtTOkenUtil.generateToken(
                employee.getEmail(),
                employee.getLevel().toString()
        );
        employee.setToken(newToken);
        employeeRepository.save((employee));
        return ResponseEntity.ok(Map.of("message", "new Token generated successfully", "newToken", newToken ));
    }


    @GetMapping("/all")  // used for fetch data or read operation
    public ResponseEntity<List<Employee>> getAllEmployees(@RequestHeader(name="Authorization",required = false) String token){
        token=token.substring(7);
        log.info(token);
        return ResponseEntity.ok(employeeService.getAllEmployee());
    }

    @PutMapping("/update/{Id}")  //used to update the data
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long Id, @RequestBody EmployeeDto request){
        return ResponseEntity.ok(employeeService.updateEmployee(Id, request));
    }


    @PostMapping("/createTask")
    public ResponseEntity<?> createTask(@ModelAttribute TaskDto request){
        return ResponseEntity.ok(taskService.createTask(request));
    }

    @GetMapping("/location/{id}")
    public ResponseEntity<?> getLocation(@RequestParam String lat,
                                         @RequestParam String Long) throws URISyntaxException, JsonProcessingException {
        return ResponseEntity.ok(locationService.getCity(lat,Long));
    }

    @PutMapping("/set")
    public ResponseEntity<?> getLocation(@RequestHeader("Authorization") String auth ,@RequestParam String lat,
                                         @RequestParam String Long) throws URISyntaxException, JsonProcessingException {
        return ResponseEntity.ok(locationService.setCity(auth.substring(7),lat,Long));
    }

}