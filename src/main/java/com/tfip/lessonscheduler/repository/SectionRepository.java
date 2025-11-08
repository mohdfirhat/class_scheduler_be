package com.tfip.lessonscheduler.repository;

import com.tfip.lessonscheduler.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface SectionRepository extends JpaRepository<Section,Long> {

    List<Section> findByTeacherIdAndDateBetween(
            Long teacherId,
            LocalDate start,
            LocalDate end
    );

    List<Section> findByTeacherIdInAndDateBetween(
            Set<Long> teacherIds,
            LocalDate start,
            LocalDate end
    );
}
