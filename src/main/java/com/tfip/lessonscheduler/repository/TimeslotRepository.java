package com.tfip.lessonscheduler.repository;

import com.tfip.lessonscheduler.entity.Timeslot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeslotRepository extends JpaRepository<Timeslot,Long> {
}
