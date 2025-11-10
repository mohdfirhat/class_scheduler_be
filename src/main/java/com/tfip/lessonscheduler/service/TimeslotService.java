package com.tfip.lessonscheduler.service;


import com.tfip.lessonscheduler.entity.Timeslot;

import java.util.List;

public interface TimeslotService {
    List<Timeslot> getAllTimeslots();
}
