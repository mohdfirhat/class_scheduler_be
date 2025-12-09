package com.tfip.lessonscheduler.service;

import com.tfip.lessonscheduler.dto.CourseDto;
import com.tfip.lessonscheduler.entity.Course;

import java.util.List;

public interface CourseService {
    List<Course> getCoursesByDepartmentId(Long department_id);
}
