package com.tfip.lessonscheduler.service;

import com.tfip.lessonscheduler.entity.Venue;

import java.util.List;

public interface VenueService {
  List<Venue> findAllByOccupancyGreaterThan(Integer classSize);
}
