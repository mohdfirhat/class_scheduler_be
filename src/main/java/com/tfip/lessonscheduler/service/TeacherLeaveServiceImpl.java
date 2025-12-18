package com.tfip.lessonscheduler.service;

import com.tfip.lessonscheduler.dto.TeacherLeaveWConflictingSectionsResponse;
import com.tfip.lessonscheduler.entity.Section;
import com.tfip.lessonscheduler.entity.Teacher;
import com.tfip.lessonscheduler.entity.TeacherLeave;
import com.tfip.lessonscheduler.entity.TeacherLeaveStatus;
import com.tfip.lessonscheduler.exception.ResourceNotFoundException;
import com.tfip.lessonscheduler.helpers.LeaveServiceHelpers;
import com.tfip.lessonscheduler.repository.SectionRepository;
import com.tfip.lessonscheduler.repository.TeacherLeaveRepository;
import com.tfip.lessonscheduler.repository.TeacherLeaveStatusRepository;
import com.tfip.lessonscheduler.repository.TeacherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Service implementation for handling teacher leave operations. This class
 * provides functionality to manage leave requests, approve or reject leaves,
 * and retrieve leave data for teachers, including conflicting sections and
 * leave statuses.
 */
@Service
public class TeacherLeaveServiceImpl implements TeacherLeaveService {

    /**
     * Repository instance responsible for accessing and performing CRUD
     * operations on teachers in the database.
     */
    private final TeacherRepository teacherRepository;
    /**
     * Repository instance responsible for accessing and performing CRUD
     * operations on sections in the database.
     */
    private final SectionRepository sectionRepository;
    /**
     * Repository instance responsible for accessing and performing CRUD
     * operations on teacher leaves in the database.
     */
    private final TeacherLeaveRepository leaveRepository;
    /**
     * Repository instance responsible for accessing and performing CRUD
     * operations on teacher leave statuses in the database.
     */
    private final TeacherLeaveStatusRepository teacherLeaveStatusRepository;

    /**
     * Constructor for TeacherLeaveServiceImpl to initialize the necessary
     * repositories.
     *
     * @param teacherRepository            Repository for accessing teacher
     *                                     data.
     * @param sectionRepository            Repository for accessing section
     *                                     data.
     * @param leaveRepository              Repository for managing teacher leave
     *                                     data.
     * @param teacherLeaveStatusRepository Repository for managing teacher leave
     *                                     status data.
     */
    public TeacherLeaveServiceImpl(TeacherRepository teacherRepository,
                                   SectionRepository sectionRepository,
                                   TeacherLeaveRepository leaveRepository,
                                   TeacherLeaveStatusRepository teacherLeaveStatusRepository) {
        this.teacherRepository = teacherRepository;
        this.sectionRepository = sectionRepository;
        this.leaveRepository = leaveRepository;
        this.teacherLeaveStatusRepository = teacherLeaveStatusRepository;
    }

    /**
     * Retrieves the leave records for all teachers who are involved in any way
     * within the conflicting schedules or availability during a specific
     * teacher's leave period.
     *
     * @param leaveId the identifier of the leave for which the teacher and the
     *                related sections need to be resolved
     * @return a list of {@code TeacherLeave} objects representing the leave
     * records of all teachers involved within the scope of the teacher taking
     * the specified leave
     */
    @Override
    public List<TeacherLeave> getLeavesOfAllTeachersInvolved(Long leaveId) {
        // Get specific leave to resolve the leave-schedule conflict
        TeacherLeave leave = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Leave with id " + leaveId + " not found"));

        // Get all section within the leave period
        List<Section> conflictingSections =
                sectionRepository.findByTeacherIdAndDateBetween(
                        leave.getTeacher().getId(),
                        leave.getStartDate(),
                        leave.getEndDate());

        // Create response object
        Set<Long> availableTeacherIds = new HashSet<>();
        // add teacherId of teacher that is taking leave
        availableTeacherIds.add(leave.getTeacher().getId());

        // Find available teachers for each schedule
        for (Section schedule : conflictingSections) {
            List<Teacher> availableTeachersForSection =
                    teacherRepository.findAllAvailableTeachersByCourseAndNotOnLeave(
                            schedule.getDate(),
                            schedule.getTimeslot().getId(),
                            schedule.getCourse().getId());

            for (Teacher teacher : availableTeachersForSection) {
                //add all available teacher to availableTeacherIds
                availableTeacherIds.add(teacher.getId());
            }
        }

        // get 3-month schedule from start leave
        LocalDate startMonth =
                leave.getStartDate()
                        .minusMonths(1)
                        .atStartOfDay()
                        .toLocalDate();
        LocalDate endMonth = leave.getStartDate()
                .plusMonths(2)
                .atTime(23, 59, 59).toLocalDate();

        return leaveRepository.findByTeacherIdInAndStartDateBetween(
                availableTeacherIds, startMonth, endMonth);
    }

    /**
     * Retrieves a TeacherLeave entity by its ID along with associated teacher
     * data.
     *
     * @param leaveId the ID of the leave to retrieve
     * @return the TeacherLeave entity corresponding to the given ID
     * @throws ResourceNotFoundException if no TeacherLeave entity with the
     *                                   specified ID is found
     */
    @Override
    public TeacherLeave getByIdWTeacher(Long leaveId) {
        TeacherLeave leave =
                leaveRepository.findById(leaveId).orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Leave with id " + leaveId + " not found"));

        return leave;
    }

    /**
     * Retrieves a list of teacher leaves that do not have a pending status.
     *
     * @return a list of TeacherLeave objects with non-pending statuses
     */
    @Override
    public List<TeacherLeave> getLeavesWithNonPendingStatus() {
        return leaveRepository.findLeavesWithNonPendingStatus();
    }

    /**
     * Retrieves a list of TeacherLeave objects that have a pending status.
     *
     * @return a list of TeacherLeave instances representing the leaves with a
     * pending status
     */
    @Override
    public List<TeacherLeave> getLeavesWithPendingStatus() {
        return leaveRepository.findLeavesWithPendingStatus();
    }

    /**
     * Retrieves a list of non-conflicting teacher leaves with a pending
     * status.
     *
     * @return a list of TeacherLeave objects representing non-conflicting
     * leaves that have a pending status.
     */
    @Override
    public List<TeacherLeave> getNonConflictingLeavesWithPendingStatus() {
        return leaveRepository.findNonConflictingLeavesWithPendingStatus();
    }

    /**
     * Retrieves a list of teacher leaves that have conflicting schedules with
     * sections and provides details about the affected sections.
     * <p>
     * This method identifies teacher leave requests that are in conflict with
     * their assigned sections for a specified time period. It fetches all
     * leaves having a pending status, checks for sections that overlap with the
     * requested leave period, and returns the results encapsulated in response
     * objects.
     *
     * @return a list of TeacherLeaveWConflictingSectionsResponse objects, each
     * containing details of the conflicting leave, including the leave
     * information and the associated affected sections.
     */
    @Override
    public List<TeacherLeaveWConflictingSectionsResponse>
    getConflictingLeavesWithAffectedSections() {
        List<TeacherLeaveWConflictingSectionsResponse>
                teacherLeavesWConflictingSections = new ArrayList<>();

        List<TeacherLeave> teacherLeaves =
                leaveRepository.findConflictingLeavesWithPendingStatus();

        if (!teacherLeaves.isEmpty()) {
            for (TeacherLeave leave : teacherLeaves) {
                List<Section> conflictingSections =
                        sectionRepository.findByTeacherIdAndDateBetween(
                                leave.getTeacher().getId(),
                                leave.getStartDate(),
                                leave.getEndDate());

                TeacherLeaveWConflictingSectionsResponse teacherLeave =
                        new TeacherLeaveWConflictingSectionsResponse(
                                leave.getId(),
                                leave.getStartDate(),
                                leave.getEndDate(),
                                leave.getStatus(),
                                leave.getTeacher(),
                                conflictingSections);
                teacherLeavesWConflictingSections.add(teacherLeave);
            }
        }
        return teacherLeavesWConflictingSections;
    }

    /**
     * Rejects a leave request with the given leave ID. If the leave does not
     * exist or is already approved/rejected, an exception is thrown. Updates
     * the leave status to "rejected" and saves the updated leave entity.
     *
     * @param leaveId the unique ID of the leave request to be rejected
     * @return a confirmation message indicating that the leave has been
     * successfully rejected
     * @throws ResourceNotFoundException if no leave with the provided ID is
     *                                   found
     * @throws IllegalStateException     if the leave is already approved or
     *                                   rejected
     */
    @Override
    @Transactional
    public String rejectLeave(Long leaveId) {
        //check if the leave exists in the database
        TeacherLeave leave =
                leaveRepository.findById(leaveId).orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Leave with id " + leaveId + " not found"));

        //check if leave has already been approved/rejected and throw an
        //exception if it has
        LeaveServiceHelpers.checkLeaveStatusStillPending(leave);

        //create a new 'rejected' status and save to leave entity, then call
        // repo to update the database
        TeacherLeaveStatus newStatus =
                teacherLeaveStatusRepository.findByType("rejected");

        leave.setStatus(newStatus);
        leaveRepository.save(leave);

        return "Leave " + leaveId + " has successfully been rejected.";
    }

    /**
     * Approves a leave request for a teacher by validating the request,
     * checking conflicts, ensuring sufficient leave days are available, and
     * updating the leave status to "approved".
     *
     * @param leaveId The unique identifier of the leave request to be
     *                approved.
     * @return A confirmation message indicating the successful approval of the
     * leave.
     * @throws ResourceNotFoundException If no leave request is found with the
     *                                   given leaveId.
     * @throws IllegalStateException     If the leave request is not in a
     *                                   pending state, has conflicts, or cannot
     *                                   be approved due to insufficient leave
     *                                   days.
     */
    @Override
    @Transactional
    public String approveLeave(Long leaveId) {

        //check if the leave exists in the database
        TeacherLeave leave =
                leaveRepository.findById(leaveId).orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Leave with id " + leaveId + " not found"));

        //check if leave has already been approved/rejected and throw an
        // exception if it has
        LeaveServiceHelpers.checkLeaveStatusStillPending(leave);

        //check if leave has new conflicts and throw an exception if it has
        List<TeacherLeaveWConflictingSectionsResponse> leavesWithConflicts =
                getConflictingLeavesWithAffectedSections();

        LeaveServiceHelpers.checkLeaveForConflicts(leaveId,
                leavesWithConflicts);

        //check if the teacher has sufficient leave days remaining to cover
        // leave duration

        LeaveServiceHelpers.checkSufficientLeaveDays(leave);

        //subtract leave duration from the number of leave days remaining and
        //update teacher in the database
        Teacher teacher = leave.getTeacher();

        // plus 1 day to offset leaves that are taken on one day which will
        // otherwise equate to 0 e.g., 18 Dec to 18 Dec
        Long leaveDuration = ChronoUnit.DAYS.between(leave.getStartDate(),
                leave.getEndDate()) + 1;
        Integer leavesRemaining =
                Math.toIntExact(teacher.getLeaveDays() - leaveDuration);

        teacher.setLeaveDays(leavesRemaining);
        teacherRepository.save(teacher);

        //create a new 'approved' status and save to leave entity, then call
        //repo to update the database
        TeacherLeaveStatus newStatus =
                teacherLeaveStatusRepository.findByType("approved");
        leave.setStatus(newStatus);

        leaveRepository.save(leave);

        return "Leave " + leaveId + " has successfully been approved.";
    }
}
