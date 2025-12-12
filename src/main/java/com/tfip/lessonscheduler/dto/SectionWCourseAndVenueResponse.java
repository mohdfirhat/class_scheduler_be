package com.tfip.lessonscheduler.dto;

import com.tfip.lessonscheduler.entity.SectionStatus;
import com.tfip.lessonscheduler.entity.Timeslot;
import com.tfip.lessonscheduler.entity.Venue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SectionWCourseAndVenueResponse {
    private Long id;
    private String remark;
    private LocalDate date;
    private Timeslot timeslot;
    private Integer classSize;
    private SectionStatus status;
    private CourseDto course;
    private Venue venue;
}
