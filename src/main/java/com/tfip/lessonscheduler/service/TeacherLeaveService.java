package com.tfip.lessonscheduler.service;

import com.tfip.lessonscheduler.dto.TeacherLeaveWTeacherResponse;

import java.util.List;

public interface TeacherLeaveService {
    List<TeacherLeaveWTeacherResponse> getLeavesOfAllTeachersInvolved(Long leaveId);

    TeacherLeaveWTeacherResponse getByIdWTeacher(Long leaveId);
}
