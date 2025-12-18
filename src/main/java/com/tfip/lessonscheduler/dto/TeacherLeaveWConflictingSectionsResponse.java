package com.tfip.lessonscheduler.dto;

import com.tfip.lessonscheduler.entity.Section;
import com.tfip.lessonscheduler.entity.Teacher;
import com.tfip.lessonscheduler.entity.TeacherLeaveStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * A response object that represents a teacher's leave request along with a list
 * of conflicting sections that overlap with the requested leave period. The
 * response includes details such as the leave ID, start and end dates, leave
 * status, teacher information, and a list of sections that conflict with the
 * requested leave.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherLeaveWConflictingSectionsResponse {

    /**
     * The id of the leave.
     */
    private Long id;
    /**
     * The starting date of the leave.
     */
    private LocalDate startDate;
    /**
     * The ending date of the leave.
     */
    private LocalDate endDate;
    /**
     * The status of the leave.
     */
    private TeacherLeaveStatus status;
    /**
     * The teacher taking the leave.
     */
    private Teacher teacher;
    /**
     * The list of sections that conflict with the leave dates.
     */
    private List<Section> conflictingSections;

}
