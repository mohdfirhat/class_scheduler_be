package com.tfip.lessonscheduler.service;


import com.tfip.lessonscheduler.entity.Timeslot;

import java.util.List;

/**
 * Service interface for managing operations related to timeslots. Provides
 * methods to retrieve a list of available timeslots.
 */
public interface TimeslotService {
    /**
     * Retrieves a list of all available timeslots.
     *
     * @return a list of {@code Timeslot} objects representing the timeslots
     */
    List<Timeslot> getTimeslots();
}
