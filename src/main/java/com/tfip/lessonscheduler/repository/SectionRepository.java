package com.tfip.lessonscheduler.repository;

import com.tfip.lessonscheduler.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface SectionRepository extends JpaRepository<Section,Long> {

    List<Section> findByTeacherIdAndStartTimeBetween(
            Long teacherId,
            LocalDateTime start,
            LocalDateTime end
    );

    List<Section> findByTeacherIdInAndStartTimeBetween(
            Set<Long> teacherIds,
            LocalDateTime start,
            LocalDateTime end
    );
}
