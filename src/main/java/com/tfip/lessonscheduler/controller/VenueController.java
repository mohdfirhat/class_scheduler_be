package com.tfip.lessonscheduler.controller;

import com.tfip.lessonscheduler.entity.Venue;
import com.tfip.lessonscheduler.service.VenueService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/venues")
public class VenueController {

  private final VenueService venueService;

  public VenueController(VenueService venueService) {
    this.venueService = venueService;
  }

  /**
   * Endpoint to get all Venues that fit the following criteria
   * <ul>
   *   <li>Available venue (no lesson on that day)</li>
   *   <li>Class Size lesser or equal to occupancy</li>
   * </ul>
   * Endpoint: {@code http://localhost:8080/api/venues} <br/>
   * Method: {@code GET} <br/>
   *
   * @return ResponseEntity with the list of Venues
   */
  @GetMapping
  public ResponseEntity<List<Venue>> getVenuesGreaterThanClassSize(
    @RequestParam Integer classSize,
    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
    @RequestParam Long timeslotId
    ) {
    return new ResponseEntity<>(venueService.findAvailableVenues(classSize,date,timeslotId), HttpStatus.OK);
  }

}
