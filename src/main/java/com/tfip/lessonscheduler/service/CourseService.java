package com.tfip.lessonscheduler.service;

import com.tfip.lessonscheduler.entity.Course;

import java.util.List;

/**
 * Service interface for managing operations related to courses. Provides
 * methods for retrieving courses based on their association with specific
 * departments.
 */
public interface CourseService {
    /**
     * Retrieves a list of courses associated with a specific department,
     * identified by the provided department ID.
     *
     * @param department_id the unique identifier of the department for which
     *                      associated courses are to be retrieved
     * @return a list of {@code Course} objects representing the courses
     * associated with the specified department
     */
    List<Course> getCoursesByDepartmentId(Long department_id);
}
