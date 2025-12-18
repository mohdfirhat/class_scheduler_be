package com.tfip.lessonscheduler.service;

import com.tfip.lessonscheduler.dto.TeacherLeaveWConflictingSectionsResponse;
import com.tfip.lessonscheduler.entity.TeacherLeave;

import java.util.List;

/**
 * Service interface for managing teacher leave requests and operations.
 * Provides methods to handle approval, rejection, and retrieval of leave
 * information involving teachers.
 */
public interface TeacherLeaveService {
    /**
     * Retrieves a list of leave records for all teachers involved in the
     * specified leave request.
     *
     * @param leaveId the unique identifier of the leave request for which the
     *                involved teachers' leave records are to be retrieved
     * @return a list of {@code TeacherLeave} objects representing the leave
     * records of all teachers associated with the specified leave request
     */
    List<TeacherLeave> getLeavesOfAllTeachersInvolved(Long leaveId);

    /**
     * Retrieves a {@code TeacherLeave} record by its unique identifier, along
     * with the associated teacher information.
     *
     * @param leaveId the unique identifier of the leave request to be
     *                retrieved
     * @return the {@code TeacherLeave} object containing the leave details and
     * the associated teacher information
     */
    TeacherLeave getByIdWTeacher(Long leaveId);

    /**
     * Retrieves a list of {@code TeacherLeave} records that have a status other
     * than "pending".
     *
     * @return a list of {@code TeacherLeave} objects with statuses indicating
     * non-pending leave requests
     */
    List<TeacherLeave> getLeavesWithNonPendingStatus();

    /**
     * Retrieves a list of {@code TeacherLeave} records that have a "pending"
     * status.
     *
     * @return a list of {@code TeacherLeave} objects representing leave records
     * with a "pending" status
     */
    List<TeacherLeave> getLeavesWithPendingStatus();

    /**
     * Retrieves a list of {@code TeacherLeave} records that have a "pending"
     * status and do not conflict with other leave requests or schedules. This
     * method ensures the retrieved leaves are non-conflicting and pending for
     * further processing or evaluation.
     *
     * @return a list of {@code TeacherLeave} objects representing
     * non-conflicting leave records with a "pending" status
     */
    List<TeacherLeave> getNonConflictingLeavesWithPendingStatus();

    /**
     * Retrieves a list of teacher leave records that conflict with other leave
     * requests or schedules, along with the associated sections affected by
     * these conflicts. The response includes detailed information about each
     * leave request, such as the leave period, leave status, associated
     * teacher, and the list of sections impacted by the conflicts.
     *
     * @return a list of {@code TeacherLeaveWConflictingSectionsResponse}
     * objects, where each object represents a conflicting leave along with the
     * sections that are affected by the conflict
     */
    List<TeacherLeaveWConflictingSectionsResponse>
    getConflictingLeavesWithAffectedSections();

    /**
     * Rejects a leave request identified by the given leave ID.
     *
     * @param leaveId the unique identifier of the leave request to be rejected
     * @return a {@code String} message indicating the status or result of the
     * rejection
     */
    String rejectLeave(Long leaveId);

    /**
     * Approves a leave request identified by the given leave ID.
     *
     * @param leaveId the unique identifier of the leave request to be approved
     * @return a {@code String} message indicating the status or result of the
     * approval
     */
    String approveLeave(Long leaveId);

}
