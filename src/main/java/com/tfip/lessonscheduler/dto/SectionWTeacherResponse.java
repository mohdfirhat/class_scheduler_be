package com.tfip.lessonscheduler.dto;

import com.tfip.lessonscheduler.entity.SectionStatus;
import com.tfip.lessonscheduler.entity.Timeslot;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SectionWTeacherResponse {
    private Long id;
    private String name;
    private String description;
    private LocalDate date;
    private Timeslot timeslot;
    private Integer classSize;
    private SectionStatus status;
    private TeacherDto teacher;
}
