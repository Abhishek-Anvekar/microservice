package com.employee.employeeservice.service;

import com.employee.employeeservice.dto.APIResponseDto;
import com.employee.employeeservice.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {

    EmployeeDto addEmployee(EmployeeDto employeeDto);
    List<EmployeeDto> getAllEmployee();
    EmployeeDto getEmployeeById(long id);
    EmployeeDto updateEmployeeById(long id, EmployeeDto employeeDto);
    String deleteEmployeeById(long id);

    APIResponseDto getEmployeeAndDepartmentByEmployeeId(long id);
}
