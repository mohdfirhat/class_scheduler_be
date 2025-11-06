package com.tfip.lessonscheduler.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherLeaveWTeacherResponse {
    private Long id;
    private LocalDate startDate;
    private LocalDate  endDate;
    private String status;
    private TeacherDto teacher;
}
