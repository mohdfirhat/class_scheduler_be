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
   *        has no lesson at that time(startTime endTime)
   *     </li>
   *     <li>
   *        teaches that subject(=subjectId)
   *     </li>
   *     <li>
   *        did not take leave that day
   *     </li>
   * </ul>
   * @param startTime start time of lesson
   * @param endTime end time of lesson
   * @param lessonDate date of lesson
   * @param subjectId id of lesson's subject
   * @return list of available Teacher
   */
  @Query("""
    SELECT t
    FROM Teacher t
    JOIN t.subjects s
    WHERE s.id = :subjectId
      AND t.manager.id IS NOT NULL
      AND NOT EXISTS (
          SELECT 1
          FROM Lesson l
          WHERE l.teacher.id = t.id
            AND l.startTime < :endTime
            AND l.endTime   > :startTime
      )
      AND NOT EXISTS (
          SELECT 1
          FROM TeacherLeave tl
          WHERE tl.teacher.id = t.id
            AND tl.startDate <= :lessonDate
            AND tl.endDate   >= :lessonDate
            AND tl.status = "approved"
      )
""")
List<Teacher> findAllAvailableTeachersBySubjectAndNotOnLeave(
        @Param("startTime") LocalDateTime startTime,
        @Param("endTime") LocalDateTime endTime,
        @Param("lessonDate") LocalDate lessonDate,
        @Param("subjectId") Long subjectId
);
}
