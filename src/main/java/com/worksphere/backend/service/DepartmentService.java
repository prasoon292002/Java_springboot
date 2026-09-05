package com.worksphere.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.worksphere.backend.dto.DepartmentRequest;
import com.worksphere.backend.dto.DepartmentResponse;
import com.worksphere.backend.entity.Department;
import com.worksphere.backend.mapper.DepartmentMapper;
import com.worksphere.backend.repository.DepartmentRepository;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    public DepartmentService(
            DepartmentRepository departmentRepository,
            DepartmentMapper departmentMapper) {

        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
    }

    public List<DepartmentResponse> getAllDepartments() {

        return departmentRepository.findAll()
                .stream()
                .map(departmentMapper::toResponse)
                .toList();
    }

    public DepartmentResponse getDepartmentById(Long id) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Department not found with id: " + id
                        )
                );

        return departmentMapper.toResponse(department);
    }

    public DepartmentResponse createDepartment(
            DepartmentRequest request) {

        if (departmentRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException(
                    "Department with name "
                            + request.getName()
                            + " already exists"
            );
        }

        Department department =
                departmentMapper.toEntity(request);

        Department savedDepartment =
                departmentRepository.save(department);

        return departmentMapper.toResponse(savedDepartment);
    }

    public DepartmentResponse updateDepartment(
            Long id,
            DepartmentRequest request) {

        Department department =
                departmentRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Department not found with id: "
                                                + id
                                )
                        );

        if (departmentRepository.existsByName(request.getName())
                && !department.getName()
                .equalsIgnoreCase(request.getName())) {

            throw new IllegalArgumentException(
                    "Department with name "
                            + request.getName()
                            + " already exists"
            );
        }

        department.setName(request.getName());
        department.setDescription(request.getDescription());

        Department updatedDepartment =
                departmentRepository.save(department);

        return departmentMapper.toResponse(updatedDepartment);
    }

    public void deleteDepartment(Long id) {

        if (!departmentRepository.existsById(id)) {
            throw new RuntimeException(
                    "Department not found with id: " + id
            );
        }

        departmentRepository.deleteById(id);
    }
}