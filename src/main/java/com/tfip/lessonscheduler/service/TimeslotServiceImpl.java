package com.tfip.lessonscheduler.service;

import com.tfip.lessonscheduler.entity.Timeslot;
import com.tfip.lessonscheduler.repository.TimeslotRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimeslotServiceImpl implements TimeslotService {

    TimeslotRepository timeslotRepository;
    public TimeslotServiceImpl(TimeslotRepository timeslotRepository) {
        this.timeslotRepository = timeslotRepository;
    }

    @Override
    public List<Timeslot> getTimeslots() {
        return timeslotRepository.findAll();
    }
}
