package com.tfip.lessonscheduler.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfip.lessonscheduler.entity.Department;

/**
 * Repository interface for managing Department entities. Extends JpaRepository
 * to provide basic CRUD operations.
 */
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
