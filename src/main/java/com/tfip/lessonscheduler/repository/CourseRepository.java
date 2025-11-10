package com.tfip.lessonscheduler.repository;

import com.tfip.lessonscheduler.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findAllByDepartmentId(Long department_id);

}
