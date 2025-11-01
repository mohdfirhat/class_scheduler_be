package com.tfip.lessonscheduler.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfip.lessonscheduler.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
