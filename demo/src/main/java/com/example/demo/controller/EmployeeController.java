package com.example.demo.controller;

import com.example.demo.model.EmployeeModel;
import com.example.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/employee")
    public ResponseEntity<List<EmployeeModel>> getAllEmployees() throws Exception {
        return employeeService.getAllEmployeeList();
    } //getAllEmployees

    @PostMapping("/employee")
    public ResponseEntity<String> addEmployee(@RequestBody EmployeeModel model) throws Exception{
        return employeeService.addEmployee(model);
    }//addEmployee

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<EmployeeModel> getEmployeeByEmployeeId(@PathVariable(value ="employeeId") String employeeId){
        return  employeeService.getEmployeeByEmployeeId(employeeId);
    } //getEmployeeByEmployeeId

    @PutMapping("/employee/{employeeId}")
    public ResponseEntity<String> updateEmployee(@PathVariable(value = "employeeId") String employeeId,
                               @RequestBody EmployeeModel model){
         return  employeeService.updateEmployee(employeeId,model);
    } //updateEmployee

    @DeleteMapping("/employee/{employeeId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable(value = "employeeId") String employeeId){
        return employeeService.deleteEmployee(employeeId);
    } //deleteEmployee

}
