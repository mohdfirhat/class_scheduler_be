package com.tfip.lessonscheduler.repository;

import com.tfip.lessonscheduler.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * Repository interface for managing Section entities. Extends JpaRepository to
 * provide basic CRUD operations.
 */
public interface SectionRepository extends JpaRepository<Section, Long> {

    /**
     * Retrieves a list of approved sections for a specific teacher within a
     * specified date range.
     *
     * @param teacherId The ID of the teacher whose sections are to be
     *                  retrieved.
     * @param start     The start date of the date range.
     * @param end       The end date of the date range.
     * @return A list of {@code Section} objects that match the given criteria.
     */
    @Query("""
                SELECT s
                FROM Section s
                WHERE s.status.type = "approved"
                AND s.teacher.id = :teacherId
                AND s.date BETWEEN :start AND :end
            """)
    List<Section> findConfirmedByTeacherIdAndDateBetween(
            Long teacherId,
            LocalDate start,
            LocalDate end
    );

    /**
     * Retrieves a list of sections for a specific teacher within a given date
     * range.
     *
     * @param teacherId The ID of the teacher whose sections are to be
     *                  retrieved.
     * @param start     The start date of the range.
     * @param end       The end date of the range.
     * @return A list of {@code Section} objects that fall within the specified
     * criteria.
     */
    List<Section> findByTeacherIdAndDateBetween(
            Long teacherId,
            LocalDate start,
            LocalDate end
    );

    /**
     * Retrieves a list of sections for multiple teachers within a given date
     * range.
     *
     * @param teacherIds A set of teacher IDs whose sections are to be
     *                   retrieved.
     * @param start      The start date of the date range.
     * @param end        The end date of the date range.
     * @return A list of {@code Section} objects that fall within the specified
     * criteria.
     */
    List<Section> findByTeacherIdInAndDateBetween(
            Set<Long> teacherIds,
            LocalDate start,
            LocalDate end
    );
}
