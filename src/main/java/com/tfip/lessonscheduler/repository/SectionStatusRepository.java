package com.tfip.lessonscheduler.repository;

import com.tfip.lessonscheduler.entity.SectionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing SectionStatus entities. Extends
 * JpaRepository to provide basic CRUD operations.
 */
public interface SectionStatusRepository extends JpaRepository<SectionStatus,
        Long> {
}
