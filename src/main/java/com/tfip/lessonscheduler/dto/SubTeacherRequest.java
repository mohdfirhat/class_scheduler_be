package com.tfip.lessonscheduler.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubTeacherRequest {
  private Long sectionId;
  private Long teacherId;
}
