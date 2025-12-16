package com.tfip.lessonscheduler.helpers;

import com.tfip.lessonscheduler.dto.TeacherLeaveWConflictingSectionsResponse;
import com.tfip.lessonscheduler.entity.Section;
import com.tfip.lessonscheduler.entity.TeacherLeave;
import com.tfip.lessonscheduler.model.LeaveUpdatingDetails;


import java.time.format.DateTimeFormatter;
import java.util.List;

public class LeaveServiceHelpers {
    // check if the leave has changed status from "pending".
    // if status has changed, return the new LeaveUpdatingDetails object with
    // status and details, else return null
    public static LeaveUpdatingDetails checkLeaveStatusStillPending(TeacherLeave leave){
        LeaveUpdatingDetails details = new LeaveUpdatingDetails();
        Long leaveId = leave.getId();
        String leaveStatus = leave.getStatus().getType();

        if(!leaveStatus.equals("pending")){
            details.setMessage("Leave "+ leaveId.toString() + " has " +
                    "already been " + leaveStatus + ".");
        }
        return details;
    }

    //check if the leave exists in the list of leaves with conflicting sections.
    // If exists, return LeaveUpdatingDetails with status and conflicting
    // sections in the message field, else return with an empty message field
    public static LeaveUpdatingDetails checkLeaveForConflicts(Long leaveId,
               List<TeacherLeaveWConflictingSectionsResponse> leaveList){

        LeaveUpdatingDetails details = new LeaveUpdatingDetails();
        if (leaveList.isEmpty()){return details;}

        TeacherLeaveWConflictingSectionsResponse leave = null;
        for(TeacherLeaveWConflictingSectionsResponse conflictingLeave: leaveList) {
            if (conflictingLeave.getId().equals(leaveId)) {
                leave = conflictingLeave;
            }
        }
        if (leave == null){ return details;}

        StringBuilder message = new StringBuilder(
                "Leave has existing conflicts with the following sections: \n\n");
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy");
        List<Section> conflictingSections =
                leave.getConflictingSections();

        for (int i = 0; i < conflictingSections.size(); i++){
            Section s = conflictingSections.get(i);
            message.append("[")
                    .append(s.getId())
                    .append("] ");

            message.append(s.getCourse().getCourseCode())
                    .append(": ");

            message.append(s.getDate().format(formatter));

            if (i != conflictingSections.size() -1){
                message.append("\n");

            } else {
                message.append("\n\n")
                        .append("Please resolve conflicts before approving " +
                                "leave.");
            }
        }
        details.setMessage(message.toString());
        return details;
    }
}
