package com.tfip.lessonscheduler.service;

import com.tfip.lessonscheduler.entity.Course;
import com.tfip.lessonscheduler.exception.ResourceNotFoundException;
import com.tfip.lessonscheduler.repository.CourseRepository;
import com.tfip.lessonscheduler.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link CourseService} interface. Provides functionality
 * for retrieving course information based on their association with specific
 * departments.
 */
@Service
public class CourseServiceImpl implements CourseService {

    /**
     * Repository interface for accessing and managing operations on Department
     * entities.
     */
    private final DepartmentRepository departmentRepository;

    /**
     * Repository interface for managing Course entities.
     */
    private final CourseRepository courseRepository;

    /**
     * Constructs a new instance of the CourseServiceImpl class with the
     * specified repositories.
     *
     * @param courseRepository     the repository for managing Course entities
     * @param departmentRepository the repository for managing Department
     *                             entities
     */
    public CourseServiceImpl(CourseRepository courseRepository,
                             DepartmentRepository departmentRepository) {
        this.courseRepository = courseRepository;
        this.departmentRepository = departmentRepository;
    }

    /**
     * Retrieves a list of courses associated with a specific department. If the
     * department with the given ID does not exist, a
     * {@code ResourceNotFoundException} is thrown.
     *
     * @param departmentId the ID of the department to fetch courses for
     * @return a list of {@code Course} entities associated with the specified
     * department
     * @throws ResourceNotFoundException if the department with the given ID
     *                                   does not exist
     */
    @Override
    public List<Course> getCoursesByDepartmentId(Long departmentId) {
        departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Department with id " + departmentId + " does not " +
                                "exist"));
        return courseRepository.findAllByDepartmentId(departmentId);
    }
}
