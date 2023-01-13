package com.example.demo.repository;

import com.example.demo.enity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

@Transactional
public interface EmployeeRepository extends JpaRepository<Employee,String> {

    @Query(nativeQuery = true , value = "select employee_id from employee where " +
            " create_time =(SELECT MAX (create_time) AS  'MaxDate' FROM employee)")
    public String findByMaxCreateTime();


}
