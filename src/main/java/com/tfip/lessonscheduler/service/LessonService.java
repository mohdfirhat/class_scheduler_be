package com.tfip.lessonscheduler.service;

import com.tfip.lessonscheduler.dto.LessonWTeacherResponse;
import com.tfip.lessonscheduler.dto.LessonWAvailableTeachersResponse;

import java.util.List;

public interface LessonService {
    List<LessonWAvailableTeachersResponse> getAllLessonsWithAvailableTeachers(Long leaveId);

    List<LessonWTeacherResponse> getAllLessonsOfAllTeachersInvolved(Long leaveId);

}
