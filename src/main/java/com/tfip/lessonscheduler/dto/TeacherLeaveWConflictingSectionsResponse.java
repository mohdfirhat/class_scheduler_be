package com.tfip.lessonscheduler.dto;

import com.tfip.lessonscheduler.entity.Section;
import com.tfip.lessonscheduler.entity.Teacher;
import com.tfip.lessonscheduler.entity.TeacherLeaveStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherLeaveWConflictingSectionsResponse {
    private Long id;
    private LocalDate startDate;
    private LocalDate  endDate;
    private TeacherLeaveStatus status;
    private Teacher teacher;
    private List<Section> conflictingSections;

}
