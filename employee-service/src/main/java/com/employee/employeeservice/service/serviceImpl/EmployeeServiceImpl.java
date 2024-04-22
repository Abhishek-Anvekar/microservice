package com.employee.employeeservice.service.serviceImpl;

import com.employee.employeeservice.dto.APIResponseDto;
import com.employee.employeeservice.dto.DepartmentDto;
import com.employee.employeeservice.dto.EmployeeDto;
import com.employee.employeeservice.entities.Employee;
import com.employee.employeeservice.exception.ResourceNotFoundException;
import com.employee.employeeservice.repositories.EmployeeRepository;
import com.employee.employeeservice.service.APIClient;
import com.employee.employeeservice.service.EmployeeService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepo;
    private ModelMapper modelMapper;

//    private WebClient webClient;
    private APIClient apiClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    @Override
    public EmployeeDto addEmployee(EmployeeDto employeeDto) {
        Employee employee = modelMapper.map(employeeDto, Employee.class);
        Employee savedEmployee = employeeRepo.save(employee);
        return modelMapper.map(savedEmployee,EmployeeDto.class);
    }

    @Override
    public List<EmployeeDto> getAllEmployee() {
        return employeeRepo.findAll().stream()
                .map(employee->modelMapper.map(employee,EmployeeDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto getEmployeeById(long id) {
        Employee employee = employeeRepo.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Employee","id",id)
        );



        return modelMapper.map(employee,EmployeeDto.class);
    }

    @Override
    public EmployeeDto updateEmployeeById(long id, EmployeeDto employeeDto) {
        Employee employee = employeeRepo.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Employee","id",id)
        );
        //employee.setId(employeeDto.getId());
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setEmail(employeeDto.getEmail());
        employee.setDepartmentCode(employeeDto.getDepartmentCode());
        Employee updatedEmployee = employeeRepo.save(employee);

        return modelMapper.map(updatedEmployee,EmployeeDto.class);
    }

    @Override
    public String deleteEmployeeById(long id) {
        employeeRepo.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Employee","id",id)
        );
        employeeRepo.deleteById(id);
        return "Employee deleted successfully";
    }

//    @CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getDefaultDepartment")
    @Retry(name = "${spring.application.name}", fallbackMethod = "getDefaultDepartment")
    @Override
    public APIResponseDto getEmployeeAndDepartmentByEmployeeId(long id) {

        LOGGER.info("Inside getEmployeeAndDepartmentByEmployeeId() method....");
        Employee employee = employeeRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee", "id", id)
        );
        EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);

        //code for WebClient
//        DepartmentDto departmentDto = webClient.get()
//                .uri("http://localhost:8080/api/departments/dept-code/" + employee.getDepartmentCode())
//                .retrieve()
//                .bodyToMono(DepartmentDto.class)
//                .block();

        //code for FeignClient
        DepartmentDto departmentDto = apiClient.getDepartmentByCode(employee.getDepartmentCode());

        APIResponseDto apiResponseDto = new APIResponseDto();
        apiResponseDto.setEmployeeDto(employeeDto);
        apiResponseDto.setDepartmentDto(departmentDto);
        return apiResponseDto;
    }

    public APIResponseDto getDefaultDepartment(long id, Exception exception) {
        LOGGER.info("Inside getDefaultDepartment() method....");

        Employee employee = employeeRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee", "id", id)
        );
        EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);


        //default DepartmentDto
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setName("R&D");
        departmentDto.setDepartmentCode("RD001");
        departmentDto.setDescription("Research And Development");

        APIResponseDto apiResponseDto = new APIResponseDto();
        apiResponseDto.setEmployeeDto(employeeDto);
        apiResponseDto.setDepartmentDto(departmentDto);
        return apiResponseDto;
    }
}
