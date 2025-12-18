package com.tfip.lessonscheduler.service;

import com.tfip.lessonscheduler.entity.Venue;

import java.time.LocalDate;
import java.util.List;

/**
 * Service interface for managing operations related to venues. Provides methods
 * for retrieving a list of venues available for scheduling based on specified
 * criteria such as class size, date, and timeslot.
 */
public interface VenueService {

    /**
     * Finds and retrieves a list of venues available for scheduling based on
     * the specified class size, date, and timeslot ID.
     *
     * @param classSize  the number of participants expected for the class; used
     *                   to determine venues that can accommodate the class
     *                   size
     * @param date       the date for which venue availability is to be checked
     * @param timeslotId the unique identifier of the timeslot for which venue
     *                   availability is to be checked
     * @return a list of {@code Venue} objects representing the venues available
     * that meet the specified criteria
     */
    List<Venue> findAvailableVenues(Integer classSize, LocalDate date,
                                    Long timeslotId);
}
