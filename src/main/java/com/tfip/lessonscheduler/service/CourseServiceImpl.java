package com.tfip.lessonscheduler.service;

import com.tfip.lessonscheduler.dto.CourseDto;
import com.tfip.lessonscheduler.mapper.CourseMapper;
import com.tfip.lessonscheduler.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    CourseRepository courseRepository;
    CourseMapper courseMapper;

    public CourseServiceImpl(CourseRepository courseRepository,CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    @Override
    public List<CourseDto> getCoursesByDepartmentId(Long departmentId) {
        return courseRepository.findAllByDepartmentId(departmentId).stream().map(courseMapper::toCourseDto).collect(Collectors.toList());
    }
}
