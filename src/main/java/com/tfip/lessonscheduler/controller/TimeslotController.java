package com.tfip.lessonscheduler.controller;

import com.tfip.lessonscheduler.entity.Timeslot;
import com.tfip.lessonscheduler.service.TimeslotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for managing timeslots. This controller provides endpoints to
 * access and manipulate timeslot-related data.
 */
@RestController
@RequestMapping("api/timeslots")
public class TimeslotController {

    /**
     * Service for managing timeslots and handling related business logic.
     */
    private final TimeslotService timeslotService;

    /**
     * Constructs a new TimeslotController with the provided TimeslotService.
     *
     * @param timeslotService the service layer dependency used for managing and
     *                        accessing timeslot-related data
     */
    public TimeslotController(TimeslotService timeslotService) {
        this.timeslotService = timeslotService;
    }

    /**
     * Endpoint to get all Timeslots <br/> Endpoint:
     * {@code http://localhost:8080/api/timeslots} <br/> Method: {@code GET}
     * <br/>
     *
     * @return ResponseEntity with the list of Timeslots
     */
    @GetMapping
    public ResponseEntity<List<Timeslot>> getTimeslots() {
        return new ResponseEntity<>(timeslotService.getTimeslots(),
                HttpStatus.OK);
    }
}
