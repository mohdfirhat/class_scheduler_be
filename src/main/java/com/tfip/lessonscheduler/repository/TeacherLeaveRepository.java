package com.tfip.lessonscheduler.repository;

import com.tfip.lessonscheduler.entity.TeacherLeave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * Repository interface for managing TeacherLeave entities. Extends
 * JpaRepository to provide basic CRUD operations.
 */
public interface TeacherLeaveRepository extends JpaRepository<TeacherLeave,
        Long> {

    /**
     * Finds a list of {@code TeacherLeave} entities for the given set of
     * teacher IDs whose start dates fall within the specified range.
     *
     * @param teacherIds a set of teacher IDs whose leave records are to be
     *                   retrieved
     * @param start      the start of the date range (inclusive)
     * @param end        the end of the date range (inclusive)
     * @return a list of {@code TeacherLeave} records that match the specified
     * teacher IDs and whose start dates fall within the provided range
     */
    List<TeacherLeave> findByTeacherIdInAndStartDateBetween(
            Set<Long> teacherIds,
            LocalDate start,
            LocalDate end
    );

    /**
     * Retrieves a list of {@code TeacherLeave} entities where the status type
     * is not "pending".
     *
     * @return a list of {@code TeacherLeave} records with non-pending status
     * types
     */
    @Query("""
            SELECT tl
            FROM TeacherLeave tl
            WHERE tl.status.type != "pending"
            """)
    List<TeacherLeave> findLeavesWithNonPendingStatus();

    /**
     * Retrieves a list of {@code TeacherLeave} entities where the status type
     * is "pending".
     *
     * @return a list of {@code TeacherLeave} records with a "pending" status
     * type
     */
    @Query("""
            SELECT tl
            FROM TeacherLeave tl
            WHERE tl.status.type = "pending"
            """)
    List<TeacherLeave> findLeavesWithPendingStatus();

    /**
     * Retrieves a list of {@code TeacherLeave} entities with a "pending"
     * status, where the associated teacher has overlapping approved
     * {@code Section} records within the leave period.
     * <p>
     * The query specifically finds leaves with a "pending" status and conflicts
     * with approved sections (having dates that fall between the leave's start
     * and end dates).
     *
     * @return a list of {@code TeacherLeave} records with "pending" status that
     * have conflicting approved {@code Section} records based on date overlap
     */
    @Query("""
            SELECT tl
            FROM TeacherLeave tl
            WHERE tl.status.type = "pending" AND
                  tl.teacher.id IN (
                    SELECT s.teacher.id
                    FROM Section s
                    WHERE s.status.type = "approved" AND
                          s.date BETWEEN tl.startDate AND tl.endDate)
            """)
    List<TeacherLeave> findConflictingLeavesWithPendingStatus();

    /**
     * Retrieves a list of {@code TeacherLeave} entities with a "pending"
     * status, where the leave does not conflict with any approved
     * {@code Section} entities.
     * <p>
     * The query ensures that the retrieved leaves do not overlap with any
     * {@code Section} records associated with the same teacher that have an
     * "approved" status and whose dates fall within the leave period.
     *
     * @return a list of {@code TeacherLeave} records with a "pending" status
     * that do not conflict with any approved {@code Section} records based on
     * date overlap
     */
    @Query("""
            SELECT tl
            FROM TeacherLeave tl
            WHERE tl.status.type = "pending"
                AND tl.id NOT IN
                (SELECT l.id
                 FROM TeacherLeave l JOIN Section s ON l.teacher.id = s.teacher.id
                 WHERE s.status.type = "approved" AND
                 s.date BETWEEN l.startDate AND l.endDate)
            """)
    List<TeacherLeave> findNonConflictingLeavesWithPendingStatus();

}
