package com.tfip.lessonscheduler.repository;

import com.tfip.lessonscheduler.entity.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface VenueRepository extends JpaRepository<Venue, Long> {


  List<Venue> findAllByOccupancyGreaterThanEqual(Integer classSize);


  @Query("""
      SELECT v
      FROM Venue v
      WHERE v.occupancy >= :classSize
        AND NOT EXISTS (
              SELECT 1
              FROM Section s
              WHERE s.venue = v
                AND s.date = :date
                AND s.timeslot.id = :timeslotId
                AND s.status.type = 'approved'
        )
    """)
  List<Venue> findAllAvailableVenue(
    @Param("classSize") Integer classSize,
    @Param("date") LocalDate date,
    @Param("timeslotId") Long timeslotId);
}
