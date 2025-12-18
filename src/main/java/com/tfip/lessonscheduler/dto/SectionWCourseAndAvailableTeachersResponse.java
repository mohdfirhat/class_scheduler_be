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

/**
 * Represents the response containing information about a section, including its
 * associated course, available teachers, and other related details. Used to
 * encapsulate all relevant information needed for section management and
 * allocation processes.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SectionWCourseAndAvailableTeachersResponse {
    /**
     * The id of the section.
     */
    private Long id;

    /**
     * Optional field to capture additional notes or comments related to the
     * section request.
     */
    private String remark;

    /**
     * The date of the section.
     */
    private LocalDate date;

    /**
     * The timeslot of the section to be created.
     */
    private Timeslot timeslot;

    /**
     * The class size of the section.
     */
    private Integer classSize;

    /**
     * The status of the section.
     */
    private SectionStatus status;

    /**
     * The course of the section.
     */
    private Course course;

    /**
     * The available teachers to cover the section.
     */
    private List<Teacher> availableTeachers;
}
