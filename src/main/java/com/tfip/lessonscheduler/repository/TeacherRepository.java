package com.tfip.lessonscheduler.repository;

import java.time.LocalDate;
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
   *        has no section at that timeslot(no section intersect timeslot)
   *     </li>
   *     <li>
   *        teaches that course(same courseId)
   *     </li>
   *     <li>
   *        did not take leave that day(no approved leave for the covering day)
   *     </li>
   * </ul>
   * @param timeslotId id of section's timeslot
   * @param sectionDate date of section
   * @param courseId id of section's course
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
  """)
  List<Teacher> findAllAvailableTeacherAtTimeslotByManagerId(
    @Param("managerId") Long managerId,
    @Param("date") LocalDate date,
    @Param("timeslotId") Long timeslotId
  );

}
