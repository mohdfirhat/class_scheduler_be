package com.tfip.lessonscheduler.service;

import com.tfip.lessonscheduler.dto.SectionCreateRequest;
import com.tfip.lessonscheduler.dto.SectionWCourseAndAvailableTeachersResponse;
import com.tfip.lessonscheduler.dto.SubTeacherRequest;
import com.tfip.lessonscheduler.entity.Course;
import com.tfip.lessonscheduler.entity.Section;
import com.tfip.lessonscheduler.entity.SectionStatus;
import com.tfip.lessonscheduler.entity.Teacher;
import com.tfip.lessonscheduler.entity.TeacherLeave;
import com.tfip.lessonscheduler.entity.Timeslot;
import com.tfip.lessonscheduler.entity.Venue;
import com.tfip.lessonscheduler.exception.AppException;
import com.tfip.lessonscheduler.exception.BusinessLogicException;
import com.tfip.lessonscheduler.exception.ResourceNotFoundException;
import com.tfip.lessonscheduler.helpers.SectionServiceHelpers;
import com.tfip.lessonscheduler.repository.CourseRepository;
import com.tfip.lessonscheduler.repository.SectionRepository;
import com.tfip.lessonscheduler.repository.SectionStatusRepository;
import com.tfip.lessonscheduler.repository.TeacherLeaveRepository;
import com.tfip.lessonscheduler.repository.TeacherRepository;
import com.tfip.lessonscheduler.repository.TimeslotRepository;
import com.tfip.lessonscheduler.repository.VenueRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implementation of the SectionService interface, providing core business logic
 * for managing sections, handling teacher availability, and resolving conflicts
 * during scheduling.
 */
@Service
public class SectionServiceImpl implements SectionService {

    /**
     * Repository instance responsible for accessing and performing CRUD
     * operations on sections in the database.
     */
    private final SectionRepository sectionRepository;
    /**
     * Repository instance responsible for accessing and performing CRUD
     * operations on teacher leaves in the database.
     */
    private final TeacherLeaveRepository teacherLeaveRepository;
    /**
     * Repository instance responsible for accessing and performing CRUD
     * operations on teachers in the database.
     */
    private final TeacherRepository teacherRepository;
    /**
     * Repository instance responsible for accessing and performing CRUD
     * operations on timeslots in the database.
     */
    private final TimeslotRepository timeslotRepository;
    /**
     * Repository instance responsible for accessing and performing CRUD
     * operations on venues in the database.
     */
    private final VenueRepository venueRepository;
    /**
     * Repository instance responsible for accessing and performing CRUD
     * operations on courses in the database.
     */
    private final CourseRepository courseRepository;
    /**
     * Repository instance responsible for accessing and performing CRUD
     * operations on section statuses in the database.
     */
    private final SectionStatusRepository sectionStatusRepository;

    /**
     * Constructs a new instance of SectionServiceImpl with the given
     * repositories.
     *
     * @param sectionRepository       the repository responsible for managing
     *                                section entities
     * @param teacherLeaveRepository  the repository responsible for managing
     *                                teacher leave entities
     * @param teacherRepository       the repository responsible for managing
     *                                teacher entities
     * @param timeslotRepository      the repository responsible for managing
     *                                timeslot entities
     * @param venueRepository         the repository responsible for managing
     *                                venue entities
     * @param courseRepository        the repository responsible for managing
     *                                course entities
     * @param sectionStatusRepository the repository responsible for managing
     *                                section status entities
     */
    public SectionServiceImpl(SectionRepository sectionRepository,
                              TeacherLeaveRepository teacherLeaveRepository,
                              TeacherRepository teacherRepository,
                              TimeslotRepository timeslotRepository,
                              VenueRepository venueRepository,
                              CourseRepository courseRepository,
                              SectionStatusRepository sectionStatusRepository) {
        this.sectionRepository = sectionRepository;
        this.teacherLeaveRepository = teacherLeaveRepository;
        this.teacherRepository = teacherRepository;
        this.timeslotRepository = timeslotRepository;
        this.venueRepository = venueRepository;
        this.courseRepository = courseRepository;
        this.sectionStatusRepository = sectionStatusRepository;
    }


    /**
     * Retrieves a list of sections that conflict with the teacher's leave
     * period and provides a list of available teachers for each conflicting
     * section.
     *
     * @param leaveId the ID of the teacher's leave for which to find
     *                conflicting sections and available teachers
     * @return a list of responses containing information about the conflicting
     * sections, including available teachers for each section
     */
    @Override
    public List<SectionWCourseAndAvailableTeachersResponse>
    getSectionsWithAvailableTeachers(Long leaveId) {

        // Get specific leave to resolve the leave-section conflict
        TeacherLeave leave = teacherLeaveRepository.findById(leaveId)
                .orElseThrow(() -> new ResourceNotFoundException("Leave with " +
                        "id " + leaveId + " not found"));


        // Get all section confirmed within the leave period
        List<Section> conflictingSections =
                sectionRepository.findConfirmedByTeacherIdAndDateBetween(
                        leave.getTeacher().getId(),
                        leave.getStartDate(),
                        leave.getEndDate());

        // Create response object
        List<SectionWCourseAndAvailableTeachersResponse> responses =
                new ArrayList<>();

        // Find available teachers for each section
        for (Section section : conflictingSections) {
            List<Teacher> availableTeachers =
                    teacherRepository.findAllAvailableTeachersByCourseAndNotOnLeave(
                            section.getDate(),
                            section.getTimeslot().getId(),
                            section.getCourse().getId());

            SectionWCourseAndAvailableTeachersResponse res =
                    new SectionWCourseAndAvailableTeachersResponse(
                            section.getId(),
                            section.getRemark(),
                            section.getDate(),
                            section.getTimeslot(),
                            section.getClassSize(),
                            section.getStatus(),
                            section.getCourse(),
                            availableTeachers
                    );

            responses.add(res);
        }

        return responses;
    }

    /**
     * Retrieves a list of sections for all teachers involved within a specified
     * leave period and a surrounding three-month timeframe.
     *
     * @param leaveId the unique ID of the teacher's leave for which sections
     *                are being resolved
     * @return a list of sections that are affected by or fall within the
     * relevant leave period including sections of teachers available during
     * this period
     */
    @Override
    public List<Section> getSectionsOfAllTeachersInvolved(Long leaveId) {
        // Get specific leave to resolve the leave-section conflict
        TeacherLeave leave = teacherLeaveRepository.findById(leaveId)
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
        // add teacherId of teacher taking leave
        availableTeacherIds.add(leave.getTeacher().getId());

        // Find available teachers for each section
        for (Section section : conflictingSections) {
            List<Teacher> availableTeachersForSection =
                    teacherRepository.findAllAvailableTeachersByCourseAndNotOnLeave(
                            section.getDate(),
                            section.getTimeslot().getId(),
                            section.getCourse().getId());

            for (Teacher teacher : availableTeachersForSection) {
                //add all available teacher to availableTeacherIds
                availableTeacherIds.add(teacher.getId());
            }
        }

        // get 3 months section from start leave
        LocalDate startMonth = leave.getStartDate().minusMonths(1);
        LocalDate endMonth = leave.getStartDate().plusMonths(2);

        return sectionRepository.findByTeacherIdInAndDateBetween(
                availableTeacherIds, startMonth, endMonth);
    }

    /**
     * Retrieves a list of all sections from the repository.
     *
     * @return a list of all Section objects available in the repository
     */
    @Override
    public List<Section> getAllSections() {
        return sectionRepository.findAll();
    }

    /**
     * Retrieves a Section by its unique identifier.
     *
     * @param sectionId the unique ID of the section to be retrieved
     * @return the Section object associated with the given ID
     * @throws ResourceNotFoundException if the section with the given ID is not
     *                                   found
     */
    @Override
    public Section getSectionById(Long sectionId) {
        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Section with id" + sectionId + "not found"));

        return section;
    }

    /**
     * Saves a new section based on the provided details. Ensures that the
     * selected teacher, venue, and timeslot are available and valid. Performs
     * checks to validate if the teacher is associated with the course and
     * verifies the availability of the venue and teacher for the specified date
     * and timeslot. If validation passes, the section is created and saved.
     *
     * @param sectionCreateRequest the request object containing details about
     *                             the new section to be created, including
     *                             teacher ID, venue ID, timeslot ID, course ID,
     *                             date, class size, and additional remarks
     * @throws IllegalArgumentException if the teacher, venue, timeslot, or
     *                                  course cannot be found
     * @throws BusinessLogicException   if the selected venue or teacher is not
     *                                  available on the specified date and
     *                                  timeslot
     * @throws AppException             if there is an error retrieving the
     *                                  initial section status from the
     *                                  repository
     */
    @Override
    @Transactional
    public void saveSection(SectionCreateRequest sectionCreateRequest) {
        // find teacher
        Teacher teacher = teacherRepository.findById(
                sectionCreateRequest.getTeacherId()).orElseThrow(() ->
                new IllegalArgumentException(
                        "Teacher with id" + sectionCreateRequest.getTeacherId()
                                + "not found"));

        // find timeslot
        Timeslot timeslot = timeslotRepository.findById(
                sectionCreateRequest.getTimeslotId()).orElseThrow(() ->
                new IllegalArgumentException(
                        "Timeslot with id" + sectionCreateRequest.getTimeslotId()
                                + "not found"));

        // find venue
        Venue venue = venueRepository.findById(
                sectionCreateRequest.getVenueId()).orElseThrow(() ->
                new IllegalArgumentException(
                        "Venue with id" + sectionCreateRequest.getVenueId()
                                + "not found"));

        // find course
        Course course = courseRepository.findById(
                sectionCreateRequest.getCourseId()).orElseThrow(() ->
                new IllegalArgumentException(
                        "Course with id" + sectionCreateRequest.getCourseId()
                                + "not found"));

        // get status pending
        SectionStatus status = sectionStatusRepository.findById(1L)
                .orElseThrow(() ->
                        new AppException(
                                "Server Error: Error with Section Status"));

        // venue available check
        List<Venue> availableVenues = venueRepository.findAllAvailableVenue(
                sectionCreateRequest.getClassSize(),
                sectionCreateRequest.getDate(),
                sectionCreateRequest.getTimeslotId());

        boolean isVenueAvailable = availableVenues.stream()
                .anyMatch(v -> v.getId().equals(venue.getId()));

        if (!isVenueAvailable) {
            throw new BusinessLogicException(
                    "Selected venue is not available for this date and " +
                            "timeslot.");
        }

        // Ensure teacher is teaching that subject
        teacherRepository.findTeacherByCourse(teacher.getId(),
                course.getId()).orElseThrow(() -> new IllegalArgumentException(
                "The teacher is not teaching the course."));

        // teacher available check
        List<Teacher> availableTeachers = teacherRepository
                .findAllAvailableTeachersByCourseAndNotOnLeave(
                        sectionCreateRequest.getDate(),
                        sectionCreateRequest.getTimeslotId(),
                        sectionCreateRequest.getCourseId());

        boolean isTeacherAvailable = availableTeachers.stream()
                .anyMatch(t -> t.getId().equals(teacher.getId()));

        if (!isTeacherAvailable) {
            throw new BusinessLogicException(
                    "Selected teacher is not available for this date and " +
                            "timeslot.");
        }

        Section newSection = new Section(null,
                sectionCreateRequest.getRemark(),
                sectionCreateRequest.getDate(),
                sectionCreateRequest.getClassSize(),
                status,
                timeslot,
                teacher,
                venue,
                course
        );

        sectionRepository.save(newSection);
    }

    /**
     * Cancels a section with the specified ID by updating its status to
     * "rejected". If the section does not exist, a
     * {@code ResourceNotFoundException} is thrown. If the section has already
     * been rejected, no further action is performed.
     *
     * @param sectionId the ID of the section to be canceled
     * @return a message indicating the successful cancellation of the section
     * @throws ResourceNotFoundException if the section with the given ID is not
     *                                   found
     */
    @Transactional
    public String cancelSection(Long sectionId) {

        //check if the section exists in the database
        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Leave with id " + sectionId + " not found."));

        //check if the section has already been approved/canceled and return if
        // it has
        SectionServiceHelpers.checkIfSectionStatusStillPending(section);

        //create new SectionStatus and assign to section, then call repo to
        // update the database
        SectionStatus newStatus = new SectionStatus(3L, "rejected");
        section.setStatus(newStatus);
        sectionRepository.save(section);
        return "Section " + sectionId + " has successfully been cancelled.";
    }

    /**
     * Cancels a section with the specified ID by updating its status to
     * "rejected" after the section has been approved. If the section does not
     * exist, a
     * {@code ResourceNotFoundException} is thrown. If the section has already
     * been rejected, no further action is performed.
     *
     * @param sectionId the ID of the section to be canceled
     * @return a message indicating the successful cancellation of the section
     * @throws ResourceNotFoundException if the section with the given ID is not
     *                                   found
     */
    @Transactional
    public String cancelApprovedSection(Long sectionId) {

        //check if the section exists in the database
        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Leave with id " + sectionId + " not found."));

        //check if the section has already been approved/canceled and return if
        // it has
        SectionServiceHelpers.checkIfSectionAlreadyRejected(section);

        //create new SectionStatus and assign to section, then call repo to
        // update the database
        SectionStatus newStatus = new SectionStatus(3L, "rejected");
        section.setStatus(newStatus);
        sectionRepository.save(section);
        return "Section " + sectionId + " has successfully been cancelled.";
    }

    /**
     * Approves a section by updating its status to "approved" in the database.
     *
     * @param sectionId the unique identifier of the section to be approved
     * @return a confirmation message indicating the successful approval of the
     * section
     * @throws ResourceNotFoundException if a section with the given ID is not
     *                                   found
     * @throws IllegalStateException     if the section's status is no longer
     *                                   pending
     */
    @Transactional
    public String approveSection(Long sectionId) {

        //check if the section exists in the database
        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Leave with id " + sectionId + " not found."));

        //check if the section has already been approved/canceled and return if
        // it has
        SectionServiceHelpers.checkIfSectionStatusStillPending(section);

        //create new SectionStatus and assign to section, then call repo to
        // update the database
        SectionStatus newStatus = new SectionStatus(2L, "approved");
        section.setStatus(newStatus);
        sectionRepository.save(section);
        return "Section " + sectionId + " has successfully been approved.";
    }

    /**
     * Updates the teacher assigned to a specific section based on the provided
     * SubTeacherRequest. Verifies the existence of the section and teacher,
     * ensures the teacher is eligible to teach the course associated with the
     * section, and checks the teacher's availability for the specified date and
     * timeslot before applying the update.
     *
     * @param subTeacherRequest the request object containing the section ID,
     *                          the ID of the new teacher to be assigned, and
     *                          any other necessary details for the update.
     * @throws IllegalArgumentException if the specified section or teacher does
     *                                  not exist.
     * @throws BusinessLogicException   if the teacher does not teach the course
     *                                  associated with the section or is not
     *                                  available for the specified date and
     *                                  timeslot.
     */
    @Override
    @Transactional
    public void updateSectionTeacher(SubTeacherRequest subTeacherRequest) {
        // Get Section
        Section section = sectionRepository.findById(
                        subTeacherRequest.getSectionId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Section with id" +
                                        subTeacherRequest.getSectionId() +
                                        "does not exist"));

        // Get Teacher
        Teacher newTeacher = teacherRepository.findById(
                subTeacherRequest.getTeacherId()).orElseThrow(() ->
                new IllegalArgumentException("Teacher with id" +
                        subTeacherRequest.getTeacherId() + "does not exist"));

        // Ensure teacher is teaching that subject
        teacherRepository.findTeacherByCourse(newTeacher.getId(),
                section.getCourse().getId()).orElseThrow(() ->
                new BusinessLogicException(
                        "New Teacher does not teach that course"));

        // Ensure that newTeacher is available
        List<Teacher> availableTeachers =
                teacherRepository.findAllAvailableTeachersByCourseAndNotOnLeave(
                        section.getDate(),
                        section.getTimeslot().getId(),
                        section.getCourse().getId());

        boolean isTeacherAvailable = availableTeachers.stream()
                .anyMatch(t -> t.getId().equals(newTeacher.getId()));

        // throw error if Teacher is not available
        if (!isTeacherAvailable) {
            throw new BusinessLogicException(
                    "Selected teacher is not available " +
                            "for this date and timeslot");
        }

        section.setTeacher(newTeacher);
        sectionRepository.save(section);
    }
}
