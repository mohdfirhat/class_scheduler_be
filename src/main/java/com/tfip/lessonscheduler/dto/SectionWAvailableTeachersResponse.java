package com.tfip.lessonscheduler.dto;

import com.tfip.lessonscheduler.entity.SectionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SectionWAvailableTeachersResponse {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer classSize;
    private SectionStatus status;
    private List<TeacherDto> availableTeachers;
}
