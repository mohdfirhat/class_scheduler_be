package com.tfip.lessonscheduler.service;

import com.tfip.lessonscheduler.entity.Venue;
import com.tfip.lessonscheduler.repository.VenueRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Implementation of the VenueService interface. Provides functionality for
 * managing operations related to venues, such as retrieving available venues
 * for scheduling based on specific criteria.
 */
@Service
public class VenueServiceImp implements VenueService {

    /**
     * Repository instance responsible for accessing and performing CRUD
     * operations on venues in the database.
     */
    private final VenueRepository venueRepository;

    /**
     * Constructs a new instance of {@code VenueServiceImp} with the specified
     * {@code VenueRepository}.
     *
     * @param venueRepository the repository used to access and perform CRUD
     *                        operations on Venue entities
     */
    public VenueServiceImp(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    /**
     * Finds and retrieves a list of venues available for scheduling based on
     * the specified class size, date, and timeslot ID.
     *
     * @param classSize  the minimum number of participants the venue must
     *                   accommodate
     * @param date       the date for which the venue availability is checked
     * @param timeslotId the unique identifier of the timeslot to check venue
     *                   availability for
     * @return a list of {@code Venue} objects that meet the specified criteria
     * and are available for the given date and timeslot
     */
    @Override
    public List<Venue> findAvailableVenues(Integer classSize, LocalDate date,
                                           Long timeslotId) {
        return venueRepository.findAllAvailableVenue(classSize, date,
                timeslotId);
    }


}
