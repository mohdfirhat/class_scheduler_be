package com.tfip.lessonscheduler.repository;

import com.tfip.lessonscheduler.entity.TeacherLeave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface TeacherLeaveRepository extends JpaRepository<TeacherLeave, Long> {
    List<TeacherLeave> findByTeacherIdInAndStartDateBetween(
            Set<Long> teacherIds,
            LocalDate start,
            LocalDate end
    );

    @Query("""
           SELECT tl
           FROM TeacherLeave tl
           WHERE tl.status.id != 1
           """)
    List<TeacherLeave> findLeavesWithNonPendingStatus();

    @Query("""
           SELECT tl
           FROM TeacherLeave tl
           WHERE tl.status.id = 1
           """)
    List<TeacherLeave> findLeavesWithPendingStatus();

    @Query("""
            SELECT tl
            FROM TeacherLeave tl
            WHERE tl.teacher.id IN (
                SELECT s.teacher.id
                FROM Section s
                WHERE s.date BETWEEN tl.startDate AND tl.endDate)
            """)
    List<TeacherLeave> findConflictingLeavesWithPendingStatus();

    @Query("""
            SELECT tl
            FROM TeacherLeave tl
            WHERE tl.status.id = 1
                AND tl.id NOT IN
                (SELECT l.id
                 FROM TeacherLeave l JOIN Section s ON l.teacher.id = s.teacher.id
                 WHERE s.date BETWEEN l.startDate AND l.endDate)
            """)
    List<TeacherLeave> findNonConflictingLeavesWithPendingStatus();
}
