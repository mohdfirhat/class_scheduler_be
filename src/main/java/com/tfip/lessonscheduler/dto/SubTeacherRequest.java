package com.tfip.lessonscheduler.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a request to substitute a teacher for a specific section. This
 * class encapsulates the section and teacher details involved in the
 * substitution process.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubTeacherRequest {

    /**
     * The id of the section.
     */
    private Long sectionId;

    /**
     * The id of the teacher.
     */
    private Long teacherId;
}
