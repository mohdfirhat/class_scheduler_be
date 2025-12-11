package com.tfip.lessonscheduler.service;

import com.tfip.lessonscheduler.entity.Venue;
import com.tfip.lessonscheduler.repository.VenueRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VenueServiceImp implements VenueService {

  private final VenueRepository venueRepository;

  public VenueServiceImp (VenueRepository venueRepository) {
    this.venueRepository = venueRepository;
  }

  @Override
  public List<Venue> findAvailableVenues(Integer classSize, LocalDate date, Long timeslotId) {
    return  venueRepository.findAllAvailableVenue(classSize,date,timeslotId);
  }



}
