package com.tfip.lessonscheduler.service;

import com.tfip.lessonscheduler.dto.TeacherLeaveWTeacherResponse;

import java.util.List;

public interface TeacherLeaveService {
    List<TeacherLeaveWTeacherResponse> getAllLeavesOfAllTeachersInvolved(Long leaveId);
}
