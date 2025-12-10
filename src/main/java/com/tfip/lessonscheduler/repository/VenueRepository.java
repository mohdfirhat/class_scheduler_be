package com.tfip.lessonscheduler.repository;

import com.tfip.lessonscheduler.entity.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VenueRepository extends JpaRepository<Venue, Long> {

  List<Venue> findAllByOccupancyGreaterThanEqual(Integer classSize);
}
