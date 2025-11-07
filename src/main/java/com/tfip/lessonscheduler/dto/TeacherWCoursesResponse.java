package com.tfip.lessonscheduler.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherWCoursesResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Integer leaveDays;
    private CourseDto[] courses;
}
