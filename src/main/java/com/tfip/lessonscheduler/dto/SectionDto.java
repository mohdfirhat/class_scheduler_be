package com.tfip.lessonscheduler.dto;

import java.time.LocalDate;

import com.tfip.lessonscheduler.entity.SectionStatus;
import com.tfip.lessonscheduler.entity.Timeslot;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SectionDto {
    private Long id;
    private String remark;
    private LocalDate date;
    private Timeslot timeslot;
    private Integer classSize;
    private SectionStatus status;
}
