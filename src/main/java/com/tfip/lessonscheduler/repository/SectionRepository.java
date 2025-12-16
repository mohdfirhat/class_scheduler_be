package com.tfip.lessonscheduler.repository;

import com.tfip.lessonscheduler.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface SectionRepository extends JpaRepository<Section,Long> {

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
