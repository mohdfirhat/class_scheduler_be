package com.tfip.lessonscheduler.dto;

import com.tfip.lessonscheduler.entity.Department;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherWCoursesAndDepartmentResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Integer leaveDays;
    private CourseDto[] courses;
    private Department department;
}
