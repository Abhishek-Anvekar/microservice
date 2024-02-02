package com.department.departmentservice.service.serviceImpl;

import com.department.departmentservice.dto.DepartmentDto;
import com.department.departmentservice.entities.Department;
import com.department.departmentservice.exception.DepartmentNotFoundException;
import com.department.departmentservice.exception.ResourceNotFoundException;
import com.department.departmentservice.repositories.DepartmentRepository;
import com.department.departmentservice.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepo;

    private ModelMapper modelMapper;
    @Override
    public DepartmentDto addDepartment(DepartmentDto departmentDto) {
        Department department = modelMapper.map(departmentDto, Department.class);
        Department savedDepartment = departmentRepo.save(department);
        return modelMapper.map(savedDepartment,DepartmentDto.class);
    }

    @Override
    public List<DepartmentDto> getAllDepartments() {
        List<Department> departments = departmentRepo.findAll();
        return departments.stream().map(department -> modelMapper.map(department,DepartmentDto.class)).collect(Collectors.toList());
    }

    @Override
    public DepartmentDto getDepartmentById(long id) {
        Department department = departmentRepo.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Department","id",id)
        );
        return modelMapper.map(department,DepartmentDto.class);
    }

    @Override
    public DepartmentDto getDepartmentByCode(String departmentCode) {
        Department department = departmentRepo.findByDepartmentCode(departmentCode).orElseThrow(
                ()-> new DepartmentNotFoundException("Department", "department code",departmentCode)
        );
        return modelMapper.map(department,DepartmentDto.class);
    }

    @Override
    public DepartmentDto updateDepartmentById(long id, DepartmentDto departmentDto) {
        Department department = departmentRepo.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Department","id",id)
        );
        department.setName(departmentDto.getName());
        department.setDescription(departmentDto.getDescription());
        department.setDepartmentCode(departmentDto.getDepartmentCode());

        Department updatedDepartment = departmentRepo.save(department);
        return modelMapper.map(updatedDepartment,DepartmentDto.class);
    }

    @Override
    public String deleteDepartmentById(long id) {
        departmentRepo.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Department","id",id)
        );
        departmentRepo.deleteById(id);
        return "Department deleted Successfully";
    }
}
