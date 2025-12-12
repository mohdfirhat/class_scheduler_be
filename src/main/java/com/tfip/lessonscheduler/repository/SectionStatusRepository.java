package com.tfip.lessonscheduler.repository;

import com.tfip.lessonscheduler.entity.SectionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionStatusRepository extends JpaRepository<SectionStatus, Long> {
}
