package com.tfip.lessonscheduler.service;

import com.tfip.lessonscheduler.dto.CourseDto;

import java.util.List;

public interface CourseService {
    List<CourseDto> getCoursesByDepartmentId(Long department_id);
}
