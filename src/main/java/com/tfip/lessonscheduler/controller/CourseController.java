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

@RestController
@RequestMapping("api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    /**
     * Endpoint to get all Courses by DepartmentId <br/>
     * Endpoint: {@code http://localhost:8080/api/courses/1}
     *
     * @param departmentId the id of the department querying
     * @return ResponseEntity with the list of Courses
     */
    @GetMapping("{departmentId}")
    public ResponseEntity<List<Course>> getCoursesByDepartmentId(@PathVariable Long departmentId) {
        return new ResponseEntity<>(courseService.getCoursesByDepartmentId(departmentId), HttpStatus.OK);
    }
}
