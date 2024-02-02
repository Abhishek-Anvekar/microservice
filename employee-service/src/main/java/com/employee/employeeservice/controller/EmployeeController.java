package com.employee.employeeservice.controller;

import com.employee.employeeservice.dto.APIResponseDto;
import com.employee.employeeservice.dto.EmployeeDto;
import com.employee.employeeservice.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/add-employee")
    ResponseEntity<EmployeeDto> addEmployee(@RequestBody EmployeeDto employeeDto){
        return new ResponseEntity<>(employeeService.addEmployee(employeeDto), HttpStatus.CREATED);
    }

    @GetMapping("/")
    ResponseEntity<List<EmployeeDto>> getAllEmployee(){
        return new ResponseEntity<>(employeeService.getAllEmployee(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable long id){
        return new ResponseEntity<>(employeeService.getEmployeeById(id),HttpStatus.OK);
    }

    @PutMapping("update/{id}")
    ResponseEntity<EmployeeDto> updateEmployeeDto(@PathVariable long id,@RequestBody EmployeeDto employeeDto){
        return new ResponseEntity<>(employeeService.updateEmployeeById(id,employeeDto),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<String> deleteEmployeeById(@PathVariable("id") long id){
        return new ResponseEntity<>(employeeService.deleteEmployeeById(id),HttpStatus.OK);
    }

    @GetMapping("/dept-client/{empId}")
    ResponseEntity<APIResponseDto> getEmployeeAndDepartmentByEmpId(@PathVariable("empId") long id){
        return new ResponseEntity<>(employeeService.getEmployeeAndDepartmentByEmployeeId(id),HttpStatus.OK);
    }

}
