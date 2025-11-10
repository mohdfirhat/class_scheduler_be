package com.tfip.lessonscheduler.controller;

import com.tfip.lessonscheduler.dto.CourseDto;
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

    private CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("{departmentId}")
    public ResponseEntity<List<CourseDto>> getCoursesByDepartmentId(@PathVariable Long departmentId) {
        return new ResponseEntity<>(courseService.getCoursesByDepartmentId(departmentId), HttpStatus.OK);
    }
}
