package com.tfip.lessonscheduler.service;

import com.tfip.lessonscheduler.dto.TeacherLeaveWTeacherResponse;
import com.tfip.lessonscheduler.entity.TeacherLeave;

import java.util.List;

public interface TeacherLeaveService {
    List<TeacherLeaveWTeacherResponse> getLeavesOfAllTeachersInvolved(Long leaveId);

    TeacherLeaveWTeacherResponse getByIdWTeacher(Long leaveId);

    List<TeacherLeave> getLeavesWithConflict();

    List<TeacherLeave> getLeavesWithPendingStatus();

    List<TeacherLeave> getNonConflictingLeavesWithPendingStatus();
}
