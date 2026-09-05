package com.worksphere.backend.mapper;

import org.springframework.stereotype.Component;

import com.worksphere.backend.dto.EmployeeRequest;
import com.worksphere.backend.dto.EmployeeResponse;
import com.worksphere.backend.entity.Department;
import com.worksphere.backend.entity.Employee;

@Component
public class EmployeeMapper {

    public Employee toEntity(
            EmployeeRequest request,
            Department department) {

        Employee employee = new Employee();

        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setDepartment(department);
        employee.setDesignation(request.getDesignation());
        employee.setSalary(request.getSalary());
        employee.setJoiningDate(request.getJoiningDate());

        return employee;
    }

    public EmployeeResponse toResponse(Employee employee) {

        EmployeeResponse response = new EmployeeResponse();

        response.setId(employee.getId());
        response.setFirstName(employee.getFirstName());
        response.setLastName(employee.getLastName());
        response.setEmail(employee.getEmail());
        response.setDepartment(
                employee.getDepartment().getName()
        );
        response.setDesignation(employee.getDesignation());
        response.setSalary(employee.getSalary());
        response.setJoiningDate(employee.getJoiningDate());

        return response;
    }
}