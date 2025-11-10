package com.tfip.lessonscheduler.mapper;

import com.tfip.lessonscheduler.dto.CourseDto;
import com.tfip.lessonscheduler.entity.Course;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {

    public CourseDto toCourseDto(Course course) {
        return new CourseDto(
                course.getId(),
                course.getName(),
                course.getCourseCode()
        );
    }
}
