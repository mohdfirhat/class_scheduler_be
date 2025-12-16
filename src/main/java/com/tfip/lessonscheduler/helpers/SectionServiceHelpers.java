package com.tfip.lessonscheduler.helpers;

import com.tfip.lessonscheduler.entity.Section;
import com.tfip.lessonscheduler.exception.StatusConflictException;

public class SectionServiceHelpers {

    public static void checkIfSectionAlreadyRejected(Section section){
        String status = section.getStatus().getType();
        if (status.equals("rejected")){
            throw new StatusConflictException("Section " + section.getId() +
                    "has already been cancelled.");
        }
    }

    public static void checkIfSectionStatusStillPending(Section section){
        String status = section.getStatus().getType();
        if(status.equals("approved")){
            throw new StatusConflictException("Section " + section.getId() +
                    "has already been approved.");
        } else if (status.equals("rejected")){
            throw new StatusConflictException("Section " + section.getId() +
                    "has already been cancelled.");
        }
    }
}
