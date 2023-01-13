package com.example.demo.service;

import com.example.demo.enity.Employee;
import com.example.demo.model.EmployeeModel;
import com.example.demo.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    EmployeeRepository employeeRepository;

    public ResponseEntity<List<EmployeeModel>> getAllEmployeeList() throws Exception{
        logger.info("getAllEmployeeList 所有員工資料");
        List<EmployeeModel> employeeModels = new ArrayList<>();
        List<Employee> employees =employeeRepository.findAll();
        if (employees.size()==0) throw new RuntimeException("沒有員工資料");
        for (Employee employee:employees){
            EmployeeModel  model= new EmployeeModel();
            model.setEmployeeId(employee.getEmployeeId());
            model.setEmail(employee.getEmail());
            model.setName(employee.getFirstName()+employee.getLastName());
            model.setAge(employee.getAge());
            employeeModels.add(model);
        } //for (EmployeeModel model
        return ResponseEntity.status(HttpStatus.OK).body(employeeModels);
    } //getAllEmployeeList

    public ResponseEntity<String> addEmployee(EmployeeModel model) throws Exception{
        logger.info("addEmployee 新增員工");
        Employee entity = new Employee();
        entity.setCreateTime(Timestamp.valueOf(LocalDateTime.now()));
        entity.setFirstName(model.getFirstName());
        entity.setLastName(model.getLastName());
        entity.setAge(model.getAge());
        entity.setEmail(model.getEmail());
        entity.setEmployeeId(getMaxEmployeeId().toString());
        employeeRepository.save(entity);
        return ResponseEntity.status(HttpStatus.OK).body("新增成功");
    }//addEmployee

    public StringBuilder getMaxEmployeeId() throws Exception{
        logger.info("getMaxEmployeeId 獲得最新員工編號");
        String maxEmployeeId = employeeRepository.findByMaxCreateTime();
        if(maxEmployeeId==null) maxEmployeeId="000";
        maxEmployeeId = maxEmployeeId.substring(1);
        Integer number = Integer.parseInt(maxEmployeeId);
        number++;
        StringBuilder builder = new StringBuilder();
        builder.append("A");
        if (number<10){
            builder.append("00"+number);
        }else if (number<99){
            builder.append("0"+number);
        }else {
            builder.append(number);
        } //if (number<10)
        return builder;
    }//getMaxEmployeeId

    public ResponseEntity<EmployeeModel>  getEmployeeByEmployeeId(String employeeId){
        logger.info("getEmployeeByEmployeeId 找到單筆員工");
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(()->
                new RuntimeException("找不到該員工:"+employeeId));
        EmployeeModel employeeModel = new EmployeeModel();
        employeeModel.setName((employee.getFirstName())+employee.getLastName());
        employeeModel.setFirstName((employee.getFirstName()));
        employeeModel.setLastName((employee.getLastName()));
        employeeModel.setEmployeeId((employee.getEmployeeId()));
        employeeModel.setAge((employee.getAge()));
        employeeModel.setEmail((employee.getEmail()));
        return ResponseEntity.status(HttpStatus.OK).body(employeeModel);
    }//getEmployeeByEmployeeId

    public ResponseEntity<String> updateEmployee(String employeeId, EmployeeModel employeeModel){
        logger.info("updateEmployee employeeId="+employeeId+"employeeModel="+employeeModel);
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(()->
                new RuntimeException("找不到該員工:"+employeeId));
        if (employee.getFirstName()==null && employee.getFirstName().equals("")) employee.setFirstName(employeeModel.getFirstName());
        if (employeeModel.getEmail()!=null) employee.setEmail(employeeModel.getEmail());
        if (employeeModel.getAge()!=null) employee.setAge(employeeModel.getAge());
        if (employeeModel.getLastName()!=null) employee.setLastName(employeeModel.getLastName());
        employee.setEditTime(Timestamp.valueOf(LocalDateTime.now()));
        employeeRepository.save(employee);
        return ResponseEntity.status(HttpStatus.OK).body("編輯成功");
    } //updateEmployee

    public ResponseEntity<String> deleteEmployee(String employeeId){
        logger.info("deleteEmployee..."+employeeId);
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(()->
                new RuntimeException("找不到該員工:"+employeeId));
        employeeRepository.delete(employee);
        return ResponseEntity.status(HttpStatus.OK).body("編輯成功");
    } //deleteEmployee

}
