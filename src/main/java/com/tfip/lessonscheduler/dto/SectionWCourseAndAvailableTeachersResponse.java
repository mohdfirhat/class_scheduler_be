package com.tfip.lessonscheduler.dto;

import com.tfip.lessonscheduler.entity.Course;
import com.tfip.lessonscheduler.entity.SectionStatus;
import com.tfip.lessonscheduler.entity.Teacher;
import com.tfip.lessonscheduler.entity.Timeslot;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SectionWCourseAndAvailableTeachersResponse {
    private Long id;
    private String remark;
    private LocalDate date;
    private Timeslot timeslot;
    private Integer classSize;
    private SectionStatus status;
    private Course course;
    private List<Teacher> availableTeachers;
}
