package com.tfip.lessonscheduler.dto;

import com.tfip.lessonscheduler.entity.SectionStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SectionWTeacherResponse {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer classSize;
    private SectionStatus status;
    private TeacherDto teacher;
}
