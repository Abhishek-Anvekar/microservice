package com.department.departmentservice.service;

import com.department.departmentservice.dto.DepartmentDto;

import java.util.List;

public interface DepartmentService {

    DepartmentDto addDepartment(DepartmentDto departmentDto);
    List<DepartmentDto> getAllDepartments();
    DepartmentDto getDepartmentById(long id);
    DepartmentDto getDepartmentByCode(String departmentCode);
    DepartmentDto updateDepartmentById(long id,DepartmentDto departmentDto);
    String deleteDepartmentById(long id);
}
