package com.tfip.lessonscheduler.dto;

import com.tfip.lessonscheduler.entity.TeacherLeaveStatus;
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
    private TeacherLeaveStatus status;
    private TeacherDto teacher;
}
