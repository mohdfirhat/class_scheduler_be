package com.tfip.lessonscheduler.service;

import com.tfip.lessonscheduler.dto.SectionCreateRequest;
import com.tfip.lessonscheduler.dto.SectionWCourseAndAvailableTeachersResponse;
import com.tfip.lessonscheduler.dto.SubTeacherRequest;
import com.tfip.lessonscheduler.entity.Section;

import java.util.List;

/**
 * Service interface for managing operations related to sections. Provides
 * methods for retrieving, creating, updating, approving, and canceling
 * sections, as well as fetching sections with available teachers or those
 * belonging to involved teachers.
 */
public interface SectionService {

    /**
     * Retrieves a list of sections, each with its associated course and a list
     * of teachers available for that section, based on the specified leave ID.
     *
     * @param leaveId the unique identifier of the leave for which the sections
     *                with available teachers are to be retrieved
     * @return a list of {@code SectionWCourseAndAvailableTeachersResponse}
     * objects, where each object contains details about the section, associated
     * course, and a list of available teachers
     */
    List<SectionWCourseAndAvailableTeachersResponse>
                                                getSectionsWithAvailableTeachers(
            Long leaveId);

    /**
     * Retrieves a list of sections that involve all teachers associated with a
     * specific leave.
     *
     * @param leaveId the unique identifier of the leave for which related
     *                sections involving all associated teachers are to be
     *                retrieved
     * @return a list of {@code Section} objects representing the sections
     * associated with the teachers involved in the specified leave
     */
    List<Section> getSectionsOfAllTeachersInvolved(Long leaveId);

    /**
     * Retrieves a list of all sections.
     *
     * @return a list of {@code Section} objects representing all available
     * sections
     */
    List<Section> getAllSections();

    /**
     * Retrieves a section based on the unique identifier provided.
     *
     * @param sectionId the unique identifier of the section to be retrieved
     * @return a {@code Section} object representing the section associated with
     * the given identifier, or {@code null} if no such section exists
     */
    Section getSectionById(Long sectionId);

    /**
     * Cancels a section identified by the given section ID.
     *
     * @param sectionId the unique identifier of the section to be canceled
     * @return a {@code String} message indicating the status or result of the
     * operation
     */
    String cancelSection(Long sectionId);

    /**
     * Approves a section identified by the given section ID.
     *
     * @param sectionId the unique identifier of the section to be approved
     * @return a {@code String} message indicating the status or result of the
     * approval operation
     */
    String approveSection(Long sectionId);

    /**
     * Saves a new section with the provided details.
     *
     * @param sectionCreateRequest an instance of {@code SectionCreateRequest}
     *                             containing details about the section to be
     *                             created, such as the course, teacher, venue,
     *                             date, timeslot, class size, and any remarks
     */
    void saveSection(SectionCreateRequest sectionCreateRequest);

    /**
     * Updates the teacher assigned to a specific section based on the provided
     * request data, which includes the section and teacher identifiers.
     *
     * @param subTeacherRequest an instance of {@code SubTeacherRequest}
     *                          containing the unique identifiers for the
     *                          section ({@code sectionId}) and the new teacher
     *                          ({@code teacherId}) to be assigned
     */
    void updateSectionTeacher(SubTeacherRequest subTeacherRequest);
}
