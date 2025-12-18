package com.tfip.lessonscheduler.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Represents a request to create a new section in the scheduling system. This
 * class encapsulates the necessary details required for creating a section,
 * such as the date, class size, and associations with timeslot, teacher, venue,
 * and course.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SectionCreateRequest {

    /**
     * Optional field to capture additional notes or comments related to the
     * section request.
     */
    private String remark;

    /**
     * The date of the section to be created.
     */
    private LocalDate date;

    /**
     * The class size of the section to be created.
     */
    private Integer classSize;

    /**
     * The timeslot id of the section to be created.
     */
    private Long timeslotId;

    /**
     * The teacher id of the teacher teaching the section to be created.
     */
    private Long teacherId;

    /**
     * The venue id of the venue that the section will be held at to be
     * created.
     */
    private Long venueId;

    /**
     * The course id of course the section is under to be created.
     */
    private Long courseId;
}
