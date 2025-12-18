package com.tfip.lessonscheduler.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfip.lessonscheduler.entity.Teacher;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository interface for managing Teacher entities. Extends JpaRepository to
 * provide basic CRUD operations.
 */
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    /**
     * Retrieves a list of all teachers managed by a specific manager.
     *
     * @param managerId the ID of the manager whose teachers are to be
     *                  retrieved
     * @return a list of Teacher entities associated with the specified manager
     * ID
     */
    List<Teacher> findAllByManagerId(Long managerId);

    /**
     * Finds a teacher associated with a specified course, ensuring the teacher
     * has a non-null manager.
     *
     * @param teacherId the ID of the teacher to be searched
     * @param courseId  the ID of the course associated with the teacher
     * @return an Optional containing the Teacher entity if found, or an empty
     * Optional if not found
     */
    @Query("""
              SELECT t
              FROM Teacher t
              JOIN t.courses c
              WHERE t.id = :teacherId
              AND c.id = :courseId
              AND t.manager.id IS NOT NULL
            """)
    Optional<Teacher> findTeacherByCourse(
            @Param("teacherId") Long teacherId,
            @Param("courseId") Long courseId
    );

    /**
     * Get available teacher that fit this description
     * <ul>
     *     <li>
     *        has no section at that timeslot(no section intersect timeslot)
     *     </li>
     *     <li>
     *        teaches that course(same courseId)
     *     </li>
     *     <li>
     *        did not take leave that day(no approved leave for the covering
     *        day)
     *     </li>
     * </ul>
     *
     * @param timeslotId  id of section's timeslot
     * @param sectionDate date of section
     * @param courseId    id of section's course
     * @return list of available Teacher
     */
    @Query("""
              SELECT t
              FROM Teacher t
              JOIN t.courses c
              WHERE c.id = :courseId
                AND t.manager.id IS NOT NULL
                AND NOT EXISTS (
                  SELECT 1
                  FROM Section s
                  WHERE s.teacher.id = t.id
                    AND s.date = :sectionDate
                    AND s.timeslot.id = :timeslotId
                )
                AND NOT EXISTS (
                  SELECT 1
                  FROM TeacherLeave tl
                  WHERE tl.teacher.id = t.id
                    AND tl.startDate <= :sectionDate
                    AND tl.endDate   >= :sectionDate
                    AND tl.status.type = "approved"
                )
            """)
    List<Teacher> findAllAvailableTeachersByCourseAndNotOnLeave(
            @Param("sectionDate") LocalDate sectionDate,
            @Param("timeslotId") Long timeslotId,
            @Param("courseId") Long courseId
    );

    /**
     * Finds all available teachers at a specific timeslot managed by a given
     * manager. A teacher is considered available if: - They do not have an
     * approved section scheduled at the specified timeslot on the given date. -
     * They do not have an approved leave on the given date. - They are assigned
     * to teach the specified course.
     *
     * @param managerId  the ID of the manager whose teachers are to be queried
     * @param date       the date for which availability is to be checked
     * @param timeslotId the ID of the timeslot for which availability is to be
     *                   checked
     * @param courseId   the ID of the course that the teacher must be assigned
     *                   to teach
     * @return a list of teachers who are available at the given timeslot
     * managed by the specified manager
     */
    @Query("""
              SELECT t
              FROM Teacher t
              WHERE t.manager.id = :managerId
                AND NOT EXISTS (
                  SELECT s
                  FROM Section s
                  WHERE s.teacher = t
                    AND s.date = :date
                    AND s.timeslot.id = :timeslotId
                    AND s.status.type = 'approved'
                )
                AND NOT EXISTS (
                  SELECT tl
                  FROM TeacherLeave tl
                  WHERE tl.teacher = t
                    AND :date BETWEEN tl.startDate AND tl.endDate
                    AND tl.status.type = 'approved'
                )
                AND :courseId IN (
                    SELECT c.id
                    FROM t.courses c
                )
            """)
    List<Teacher> findAllAvailableTeacherAtTimeslotByManagerId(
            @Param("managerId") Long managerId,
            @Param("date") LocalDate date,
            @Param("timeslotId") Long timeslotId,
            @Param("courseId") Long courseId
    );
}
