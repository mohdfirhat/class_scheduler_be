package com.tfip.lessonscheduler.dto;

import com.tfip.lessonscheduler.entity.SectionStatus;
import com.tfip.lessonscheduler.entity.Timeslot;
import com.tfip.lessonscheduler.entity.Venue;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SectionWCourseAndVenueAndTeacherResponse {
    private Long id;
    private String name;
    private String description;
    private LocalDate date;
    private Timeslot timeslot;
    private Integer classSize;
    private SectionStatus status;
    private CourseDto course;
    private Venue venue;
    private TeacherDto teacher;
}
