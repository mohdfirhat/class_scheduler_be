package com.tfip.lessonscheduler.controller;

import com.tfip.lessonscheduler.entity.Timeslot;
import com.tfip.lessonscheduler.service.TimeslotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/timeslots")
public class TimeslotController {

    private TimeslotService timeslotService;

    public TimeslotController(TimeslotService timeslotService) {
        this.timeslotService = timeslotService;
    }

    @GetMapping
    public ResponseEntity<List<Timeslot>> getTimeslots() {
        return new ResponseEntity<>(timeslotService.getTimeslots(), HttpStatus.OK);
    }
}
