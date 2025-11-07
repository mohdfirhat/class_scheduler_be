package com.tfip.lessonscheduler.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfip.lessonscheduler.entity.Teacher;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
  List<Teacher> findAllByManagerId(Long managerId);

  /**
   * Get available teacher that fit this description
   * <ul>
   *     <li>
   *        has no section at that time(startTime endTime)
   *     </li>
   *     <li>
   *        teaches that course(=courseId)
   *     </li>
   *     <li>
   *        did not take leave that day
   *     </li>
   * </ul>
   * @param startTime start time of section
   * @param endTime end time of section
   * @param sectionDate date of section
   * @param courseId id of section's course
   * @return list of available Teacher
   */
  @Query("""
    SELECT t
    FROM Teacher t
    JOIN t.courses s
    WHERE s.id = :courseId
      AND t.manager.id IS NOT NULL
      AND NOT EXISTS (
          SELECT 1
          FROM Section l
          WHERE l.teacher.id = t.id
            AND l.startTime < :endTime
            AND l.endTime   > :startTime
      )
      AND NOT EXISTS (
          SELECT 1
          FROM TeacherLeave tl
          WHERE tl.teacher.id = t.id
            AND tl.startDate <= :sectionDate
            AND tl.endDate   >= :sectionDate
            AND tl.status = "approved"
      )
""")
List<Teacher> findAllAvailableTeachersByCourseAndNotOnLeave(
        @Param("startTime") LocalDateTime startTime,
        @Param("endTime") LocalDateTime endTime,
        @Param("sectionDate") LocalDate sectionDate,
        @Param("courseId") Long courseId
);
}
