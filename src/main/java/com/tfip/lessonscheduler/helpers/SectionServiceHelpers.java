package com.tfip.lessonscheduler.helpers;

import com.tfip.lessonscheduler.entity.Section;
import com.tfip.lessonscheduler.exception.StatusConflictException;

/**
 * Helper class used by SectionService
 */
public class SectionServiceHelpers {

    /**
     * Function for checking if a section status has changed to "rejected" in
     * the database by checking the section status type
     *
     * @param section Section to be checked
     */
    public static void checkIfSectionAlreadyRejected(Section section) {
        String status = section.getStatus().getType();
        if (status.equals("rejected")) {
            throw new StatusConflictException("Section " + section.getId() +
                    "has already been cancelled.");
        }
    }

    /**
     * Function for checking if a section status has changed from "pending" in
     * the database by checking the section status type
     *
     * @param section Section to be checked
     */
    public static void checkIfSectionStatusStillPending(Section section) {
        String status = section.getStatus().getType();
        if (status.equals("approved")) {
            throw new StatusConflictException("Section " + section.getId() +
                    "has already been approved.");
        } else if (status.equals("rejected")) {
            throw new StatusConflictException("Section " + section.getId() +
                    "has already been cancelled.");
        }
    }
}
