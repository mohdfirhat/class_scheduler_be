package com.tfip.lessonscheduler.repository;

import com.tfip.lessonscheduler.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface LessonRepository extends JpaRepository<Lesson,Long> {

    List<Lesson> findByTeacherIdAndStartTimeBetween(
            Long teacherId,
            LocalDateTime start,
            LocalDateTime end
    );

    List<Lesson> findByTeacherIdInAndStartTimeBetween(
            Set<Long> teacherIds,
            LocalDateTime start,
            LocalDateTime end
    );
}
