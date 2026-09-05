package com.worksphere.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.worksphere.backend.dto.EmployeeRequest;
import com.worksphere.backend.dto.EmployeeResponse;
import com.worksphere.backend.entity.Department;
import com.worksphere.backend.entity.Employee;
import com.worksphere.backend.exception.EmployeeNotFoundException;
import com.worksphere.backend.mapper.EmployeeMapper;
import com.worksphere.backend.repository.DepartmentRepository;
import com.worksphere.backend.repository.EmployeeRepository;


@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
private final DepartmentRepository departmentRepository;
   
public EmployeeService(
        EmployeeRepository employeeRepository,
        EmployeeMapper employeeMapper,
        DepartmentRepository departmentRepository) {

    this.employeeRepository = employeeRepository;
    this.employeeMapper = employeeMapper;
    this.departmentRepository = departmentRepository;
}

    public List<EmployeeResponse> getAllEmployees() {

        return employeeRepository.findAll()
                .stream()
                .map(employeeMapper::toResponse)
                .toList();
    }

    public EmployeeResponse getEmployeeById(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(id)
                );

        return employeeMapper.toResponse(employee);
    }

    public EmployeeResponse createEmployee(EmployeeRequest request) {

        if (employeeRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException(
                    "Employee with email " + request.getEmail()
                            + " already exists"
            );
        }

        Department department = departmentRepository.findById(
        request.getDepartmentId()
).orElseThrow(() ->
        new RuntimeException(
                "Department not found with id: "
                        + request.getDepartmentId()
        )
);

Employee employee = employeeMapper.toEntity(
        request,
        department
);

        Employee savedEmployee = employeeRepository.save(employee);

        return employeeMapper.toResponse(savedEmployee);
    }

    public EmployeeResponse updateEmployee(
        Long id,
        EmployeeRequest request) {

    Employee employee = employeeRepository.findById(id)
            .orElseThrow(() ->
                    new EmployeeNotFoundException(id)
            );

    if (employeeRepository.existsByEmailAndIdNot(
            request.getEmail(), id)) {

        throw new IllegalArgumentException(
                "Employee with email " + request.getEmail()
                        + " already exists"
        );
    }

        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        Department department = departmentRepository.findById(
        request.getDepartmentId()
).orElseThrow(() ->
        new RuntimeException(
                "Department not found with id: "
                        + request.getDepartmentId()
        )
);

employee.setDepartment(department);
        employee.setDesignation(request.getDesignation());
        employee.setSalary(request.getSalary());
        employee.setJoiningDate(request.getJoiningDate());

        Employee updatedEmployee =
                employeeRepository.save(employee);

        return employeeMapper.toResponse(updatedEmployee);
    }

    public void deleteEmployee(Long id) {

        if (!employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException(id);
        }

        employeeRepository.deleteById(id);
    }
}