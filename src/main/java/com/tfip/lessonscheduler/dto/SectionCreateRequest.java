package com.tfip.lessonscheduler.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SectionCreateRequest {
  private String remark;
  private LocalDate date;
  private Integer classSize;
  private Long timeslotId;
  private Long teacherId;
  private Long venueId;
  private Long courseId;
}
