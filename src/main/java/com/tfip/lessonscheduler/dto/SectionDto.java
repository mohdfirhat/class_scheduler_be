package com.tfip.lessonscheduler.dto;

import java.time.LocalDateTime;

import com.tfip.lessonscheduler.entity.SectionStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SectionDto {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer classSize;
    private SectionStatus status;
}
