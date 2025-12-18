package com.tfip.lessonscheduler.controller;

import com.tfip.lessonscheduler.entity.Course;
import com.tfip.lessonscheduler.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for managing courses. This controller provides endpoints to
 * access and manipulate course-related data.
 */
@RestController
@RequestMapping("api/courses")
public class CourseController {

    /**
     * Service layer dependency used for managing and accessing course-related
     * data.
     */
    private final CourseService courseService;

    /**
     * Constructs a new CourseController with the given CourseService.
     *
     * @param courseService the service layer dependency used for managing and
     *                      accessing course-related data
     */
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    /**
     * Retrieves a list of courses associated with a specific department.
     *
     * @param departmentId the ID of the department for which courses need to be
     *                     retrieved
     * @return a ResponseEntity containing a list of courses associated with the
     * department
     */
    @GetMapping("{departmentId}")
    public ResponseEntity<List<Course>>
    getCoursesByDepartmentId(@PathVariable Long departmentId) {
        return new ResponseEntity<>(
                courseService.getCoursesByDepartmentId(departmentId),
                HttpStatus.OK);
    }
}
