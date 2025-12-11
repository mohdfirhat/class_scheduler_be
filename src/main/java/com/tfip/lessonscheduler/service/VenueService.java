package com.tfip.lessonscheduler.service;

import com.tfip.lessonscheduler.entity.Venue;

import java.time.LocalDate;
import java.util.List;

public interface VenueService {
  List<Venue> findAvailableVenues(Integer classSize, LocalDate date, Long timeslotId);
}
