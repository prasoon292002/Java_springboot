package com.worksphere.backend.mapper;

import org.springframework.stereotype.Component;

import com.worksphere.backend.dto.DepartmentRequest;
import com.worksphere.backend.dto.DepartmentResponse;
import com.worksphere.backend.entity.Department;

@Component
public class DepartmentMapper {

    public Department toEntity(DepartmentRequest request) {

        Department department = new Department();

        department.setName(request.getName());
        department.setDescription(request.getDescription());

        return department;
    }

    public DepartmentResponse toResponse(Department department) {

        return new DepartmentResponse(
                department.getId(),
                department.getName(),
                department.getDescription()
        );
    }
}