package com.tfip.lessonscheduler.service;

import com.tfip.lessonscheduler.dto.CourseDto;
import com.tfip.lessonscheduler.entity.Course;
import com.tfip.lessonscheduler.exception.ResourceNotFoundException;
import com.tfip.lessonscheduler.mapper.CourseMapper;
import com.tfip.lessonscheduler.repository.CourseRepository;
import com.tfip.lessonscheduler.repository.DepartmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    private final DepartmentRepository departmentRepository;
    CourseRepository courseRepository;
    CourseMapper courseMapper;

    public CourseServiceImpl(CourseRepository courseRepository, CourseMapper courseMapper, DepartmentRepository departmentRepository) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<Course> getCoursesByDepartmentId(Long departmentId) {
        departmentRepository.findById(departmentId).orElseThrow(()->new ResourceNotFoundException("Department with id " + departmentId + " does not exist"));
        return courseRepository.findAllByDepartmentId(departmentId);
    }
}
