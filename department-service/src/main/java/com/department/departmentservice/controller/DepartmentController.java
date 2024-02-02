package com.department.departmentservice.controller;

import com.department.departmentservice.dto.DepartmentDto;
import com.department.departmentservice.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/departments")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/add-department")
    ResponseEntity<DepartmentDto> addDepartment(@RequestBody DepartmentDto departmentDto){
        return new ResponseEntity<>(departmentService.addDepartment(departmentDto), HttpStatus.CREATED);
    }

    @GetMapping("/")
    ResponseEntity<List<DepartmentDto>> getAllDepartment(){
        return new ResponseEntity<>(departmentService.getAllDepartments(),HttpStatus.OK);
    }

    @GetMapping("/dept-code/{departmentCode}")
    ResponseEntity<DepartmentDto> getDepartmentByCode(@PathVariable String departmentCode){
        return new ResponseEntity<>(departmentService.getDepartmentByCode(departmentCode),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable("id") long id){
        return new ResponseEntity<>(departmentService.getDepartmentById(id),HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    ResponseEntity<DepartmentDto> updateDepartmentById(@PathVariable("id") long id,@RequestBody DepartmentDto departmentDto){
        return new ResponseEntity<>(departmentService.updateDepartmentById(id,departmentDto),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<String> deleteDepartmentById(@PathVariable("id") long id){
        return new ResponseEntity<>(departmentService.deleteDepartmentById(id),HttpStatus.OK);
    }
}
