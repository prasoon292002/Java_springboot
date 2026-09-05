package com.worksphere.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.worksphere.backend.entity.Department;

public interface DepartmentRepository
        extends JpaRepository<Department, Long> {

    boolean existsByName(String name);
}