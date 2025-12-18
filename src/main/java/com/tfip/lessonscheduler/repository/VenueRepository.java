package com.tfip.lessonscheduler.repository;

import com.tfip.lessonscheduler.entity.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository interface for managing Venue entities. Extends JpaRepository to
 * provide basic CRUD operations.
 */
public interface VenueRepository extends JpaRepository<Venue, Long> {

    /**
     * Finds all available venues that meet the specified requirements. A venue
     * is considered available if its occupancy is greater than or equal to the
     * specified class size and it does not have an approved section scheduled
     * on the given date and timeslot.
     *
     * @param classSize  the minimum occupancy required for the venue
     * @param date       the specific date of the scheduled session
     * @param timeslotId the ID of the timeslot to check availability for
     * @return a list of venues that meet the requirements and are available
     */
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
