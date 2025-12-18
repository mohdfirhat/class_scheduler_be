package com.tfip.lessonscheduler.repository;

import com.tfip.lessonscheduler.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for managing Course entities. Extends JpaRepository to
 * provide basic CRUD operations.
 */
public interface CourseRepository extends JpaRepository<Course, Long> {

    /**
     * Retrieves all Course entities that belong to the specified department.
     *
     * @param department_id the ID of the department for which courses are to be
     *                      retrieved
     * @return a list of Course entities belonging to the specified department
     */
    List<Course> findAllByDepartmentId(Long department_id);

}
