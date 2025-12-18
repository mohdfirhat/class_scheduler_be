package com.tfip.lessonscheduler.service;

import com.tfip.lessonscheduler.entity.Timeslot;
import com.tfip.lessonscheduler.repository.TimeslotRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link TimeslotService} interface. Provides business
 * logic related to operations on timeslots.
 */
@Service
public class TimeslotServiceImpl implements TimeslotService {

    /**
     * Repository interface for accessing and managing operations on Timeslot
     * entities.
     */
    TimeslotRepository timeslotRepository;

    /**
     * Constructs a new instance of TimeslotServiceImpl and initializes it with
     * the specified TimeslotRepository for managing Timeslot entity
     * operations.
     *
     * @param timeslotRepository the repository instance for accessing and
     *                           managing Timeslot entities
     */
    public TimeslotServiceImpl(TimeslotRepository timeslotRepository) {
        this.timeslotRepository = timeslotRepository;
    }

    /**
     * Retrieves a list of all available timeslots.
     *
     * @return a list of {@code Timeslot} objects representing all the timeslots
     * stored in the system
     */
    @Override
    public List<Timeslot> getTimeslots() {
        return timeslotRepository.findAll();
    }
}
