package com.tfip.lessonscheduler.service;

import java.time.LocalDate;
import java.util.List;

import com.tfip.lessonscheduler.entity.Teacher;
import com.tfip.lessonscheduler.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.tfip.lessonscheduler.repository.TeacherRepository;

/**
 * Implementation of the TeacherService interface. This service provides
 * operations for managing and retrieving teacher data, including handling
 * relationships with courses, departments, leaves, and availability.
 */
@Service
public class TeacherServiceImpl implements TeacherService {

    /**
     * Repository instance responsible for accessing and performing CRUD
     * operations on teachers in the database.
     */
    private final TeacherRepository teacherRepository;

    /**
     * Constructs a new TeacherServiceImpl with the specified
     * TeacherRepository.
     *
     * @param teacherRepository The repository instance used to perform CRUD
     *                          operations and data access for Teacher
     *                          entities.
     */
    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    /**
     * Retrieves a list of all teachers from the database.
     *
     * @return a list of Teacher entities representing all teachers available in
     * the system.
     */
    @Override
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    /**
     * Retrieves a list of teachers associated with their courses and department
     * for a specified manager.
     *
     * @param managerId The unique identifier of the manager whose associated
     *                  teachers, courses, and departments are to be retrieved.
     * @return A list of Teacher entities containing information about each
     * teacher and their respective courses and department.
     */
    //TODO: remove one of them
    @Override
    public List<Teacher> getTeachersWithCoursesAndDepartment(Long managerId) {
        return teacherRepository.findAllByManagerId(managerId);
    }

    /**
     * Retrieves a list of teachers under the specified manager and their leaves
     * from the database.
     *
     * @param managerId The unique identifier of the manager whose associated
     *                  teachers with leaves are to be retrieved.
     * @return A list of Teacher entities representing teachers who have
     * recorded leaves under the specified manager.
     */
    //TODO: remove one of them
    @Override
    public List<Teacher> getTeachersWithLeaves(Long managerId) {
        return teacherRepository.findAllByManagerId(managerId);
    }

    /**
     * Retrieves a teacher entity by its ID, including the teacher's associated
     * leaves and sections.
     *
     * @param teacherId The unique identifier of the teacher to be retrieved.
     * @return The Teacher entity containing information about the teacher,
     * their leaves, and sections.
     * @throws ResourceNotFoundException if no teacher with the specified ID is
     *                                   found.
     */
    //TODO: remove one of them
    @Override
    public Teacher getTeacherWithLeavesAndSections(Long teacherId) {
        Teacher teacher =
                teacherRepository.findById(teacherId)
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Teacher with id " + teacherId + " not found"));
        return teacher;
    }

    /**
     * Retrieves a list of teachers who are available at a specific timeslot for
     * a given manager.
     *
     * @param managerId  The unique identifier of the manager to filter teachers
     *                   by.
     * @param date       The date for which the availability is being checked.
     * @param timeslotId The unique identifier of the timeslot to determine
     *                   teacher availability for.
     * @param courseId   The unique identifier of the course to further filter
     *                   teacher availability.
     * @return A list of Teacher entities that are available at the specified
     * timeslot for the given manager, date, and course.
     */
    @Override
    public List<Teacher> getAvailableTeacherAtTimeslotByManagerId(
            Long managerId, LocalDate date, Long timeslotId, Long courseId) {
        List<Teacher> availableTeachers =
                teacherRepository.findAllAvailableTeacherAtTimeslotByManagerId(
                        managerId, date, timeslotId, courseId);
        return availableTeachers;
    }
}