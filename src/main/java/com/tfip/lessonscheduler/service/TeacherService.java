package com.tfip.lessonscheduler.service;

import java.time.LocalDate;
import java.util.List;

import com.tfip.lessonscheduler.entity.Teacher;

/**
 * Service interface for managing operations related to teachers. Provides
 * methods to retrieve, filter, and manage teacher data, including associated
 * courses, departments, leaves, and teaching schedules.
 */
public interface TeacherService {

    /**
     * Retrieves a list of all teachers.
     *
     * @return a list of {@code Teacher} objects representing all teachers
     */
    List<Teacher> getAllTeachers();

    /**
     * Retrieves a list of teachers along with their associated courses and
     * departments, filtered based on the manager ID provided.
     *
     * @param managerId the unique identifier of the manager used to filter the
     *                  teachers who are managed by this manager
     * @return a list of {@code Teacher} objects, each including their
     * associated courses, and department information
     */
    List<Teacher> getTeachersWithCoursesAndDepartment(Long managerId);

    /**
     * Retrieves a list of teachers who have recorded leaves, filtered by the
     * manager ID. The method considers teachers that are managed by the
     * specified manager.
     *
     * @param managerId the unique identifier of the manager used to filter the
     *                  list of teachers who report to this manager
     * @return a list of {@code Teacher} objects representing teachers with
     * recorded leaves under the specified manager
     */
    List<Teacher> getTeachersWithLeaves(Long managerId);

    /**
     * Retrieves a single {@code Teacher} entity along with its associated
     * leaves and sections based on the provided teacher ID.
     *
     * @param teacherId the unique identifier of the teacher to retrieve
     * @return the {@code Teacher} object containing information about the
     * teacher, along with their associated leaves and sections
     */
    Teacher getTeacherWithLeavesAndSections(Long teacherId);

    /**
     * Retrieves a list of available teachers for a specific timeslot, date, and
     * course filtered by manager ID. This method is used to identify teachers
     * who are available to teach during the specified criteria under a given
     * manager's responsibility.
     *
     * @param managerId  the unique identifier of the manager to filter teachers
     *                   managed by them
     * @param date       the specific date for which the availability of
     *                   teachers is to be checked
     * @param timeslotId the unique identifier of the timeslot for which teacher
     *                   availability is required
     * @param courseId   the unique identifier of the course for which teacher
     *                   availability is evaluated
     * @return a list of {@code Teacher} entities representing available
     * teachers matching the criteria
     */
    List<Teacher> getAvailableTeacherAtTimeslotByManagerId(Long managerId,
                                                           LocalDate date,
                                                           Long timeslotId,
                                                           Long courseId);

}
