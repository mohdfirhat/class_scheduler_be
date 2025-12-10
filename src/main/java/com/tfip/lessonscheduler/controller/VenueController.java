package com.tfip.lessonscheduler.controller;

import com.tfip.lessonscheduler.entity.Venue;
import com.tfip.lessonscheduler.service.VenueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/venues")
public class VenueController {

  private final VenueService venueService;

  public VenueController(VenueService venueService) {
    this.venueService = venueService;
  }

  @GetMapping("")
  public ResponseEntity<List<Venue>> getVenuesGreaterThanClassSize(@RequestParam Integer classSize) {
    return new ResponseEntity<>(venueService.findAllByOccupancyGreaterThan(classSize), HttpStatus.OK);
  }

}
