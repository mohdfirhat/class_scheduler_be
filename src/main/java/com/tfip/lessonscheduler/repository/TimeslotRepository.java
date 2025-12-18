package com.tfip.lessonscheduler.repository;

import com.tfip.lessonscheduler.entity.Timeslot;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing Timeslots entities. Extends JpaRepository
 * to provide basic CRUD operations.
 */
public interface TimeslotRepository extends JpaRepository<Timeslot, Long> {
}
