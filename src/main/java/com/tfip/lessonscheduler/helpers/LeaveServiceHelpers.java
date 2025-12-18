package com.tfip.lessonscheduler.helpers;

import com.tfip.lessonscheduler.dto.TeacherLeaveWConflictingSectionsResponse;
import com.tfip.lessonscheduler.entity.Section;
import com.tfip.lessonscheduler.entity.Teacher;
import com.tfip.lessonscheduler.entity.TeacherLeave;
import com.tfip.lessonscheduler.exception.StatusConflictException;


import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Helper class used by LeaveService
 */
public class LeaveServiceHelpers {

    /**
     * Checks if the status of the provided leave is still "pending". If the
     * status is not "pending", an exception is thrown indicating that the leave
     * has already been processed with a different status.
     *
     * @param leave the {@code TeacherLeave} object to be checked, containing
     *              details about the leave request, including its status.
     * @throws StatusConflictException if the leave status is not "pending".
     */
    public static void checkLeaveStatusStillPending(TeacherLeave leave) {
        Long leaveId = leave.getId();
        String leaveStatus = leave.getStatus().getType();

        if (!leaveStatus.equals("pending")) {
            throw new StatusConflictException("Leave " + leaveId.toString() +
                    " has already been " + leaveStatus + ".");
        }
    }

    /**
     * Examines whether a specified leave exists in the provided list of leaves
     * with potentially conflicting sections. If a conflict is detected, the
     * method throws a {@code StatusConflictException} with a detailed message
     * specifying the conflicting sections. If no conflicts are found, the
     * method simply returns without any action.
     *
     * @param leaveId   the unique identifier of the leave being checked for
     *                  conflicts
     * @param leaveList the list of
     *                  {@code TeacherLeaveWConflictingSectionsResponse} objects
     *                  containing leave details and possible conflicting
     *                  sections
     * @throws StatusConflictException if the leave is found with conflicting
     *                                 sections
     */
    public static void checkLeaveForConflicts(Long leaveId,
                  List<TeacherLeaveWConflictingSectionsResponse> leaveList) {

        if (leaveList.isEmpty()) {
            return;
        }

        TeacherLeaveWConflictingSectionsResponse leave = null;
        for (TeacherLeaveWConflictingSectionsResponse conflictingLeave :
                leaveList) {
            if (conflictingLeave.getId().equals(leaveId)) {
                leave = conflictingLeave;
            }
        }
        if (leave == null) {
            return;
        }

        StringBuilder message = new StringBuilder(
                "Leave has existing conflicts with the following sections: " +
                        "\n\n");
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy");
        List<Section> conflictingSections =
                leave.getConflictingSections();

        for (int i = 0; i < conflictingSections.size(); i++) {
            Section s = conflictingSections.get(i);
            message.append("[")
                    .append(s.getId())
                    .append("] ");

            message.append(s.getCourse().getCourseCode())
                    .append(": ");

            message.append(s.getDate().format(formatter));

            if (i != conflictingSections.size() - 1) {
                message.append("\n");

            } else {
                message.append("\n\n")
                        .append("Please resolve conflicts before approving " +
                                "leave.");
            }
        }
        throw new StatusConflictException(message.toString());
    }

    /**
     * Checks if the teacher associated with the provided leave has sufficient
     * leave days remaining to accommodate the requested leave duration. If the
     * teacher does not have enough leave days, an
     * {@code IllegalArgumentException} is thrown.
     *
     * @param leave the {@code TeacherLeave} object containing information about
     *              the leave request, including start and end dates, and the
     *              teacher requesting the leave.
     * @throws IllegalArgumentException if the teacher does not have enough
     *                                  leave days.
     */
    public static void checkSufficientLeaveDays(TeacherLeave leave) {
        // plus 1 day to offset leaves that are taken on one day which will
        // otherwise equate to 0 e.g., 18 Dec to 18 Dec
        Long leaveDuration = ChronoUnit.DAYS.between(leave.getStartDate(),
                leave.getEndDate()) + 1;

        Teacher teacher = leave.getTeacher();
        String message = "Leave approval failed. " + teacher.getFirstName() +
                " " + teacher.getLastName() + " has insufficient leaves.";

        if (teacher.getLeaveDays() < leaveDuration) {
            throw new IllegalArgumentException(message);
        }
    }
}
