package com.tfip.lessonscheduler.dto;

import java.time.LocalDate;

import com.tfip.lessonscheduler.entity.TeacherLeaveStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherLeaveDto {
    private Long id;
    private LocalDate startDate;
    private LocalDate  endDate;
    private TeacherLeaveStatus status;
}
