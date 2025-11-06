package com.tfip.lessonscheduler.repository;

import com.tfip.lessonscheduler.entity.TeacherLeave;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface TeacherLeaveRepository extends JpaRepository<TeacherLeave, Long> {
    List<TeacherLeave> findByTeacherIdInAndStartDateBetween(
            Set<Long> teacherIds,
            LocalDate start,
            LocalDate end
    );
}
