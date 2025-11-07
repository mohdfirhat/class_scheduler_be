package com.tfip.lessonscheduler.service;

import java.util.List;

import com.tfip.lessonscheduler.dto.TeacherWLeavesResponse;
import com.tfip.lessonscheduler.dto.TeacherWCoursesResponse;
import com.tfip.lessonscheduler.dto.TeacherWithLeavesAndLessonsResponse;

public interface TeacherService {
  List<TeacherWCoursesResponse> getTeachersWithCourses(Long managerId);

  List<TeacherWLeavesResponse> getTeachersWithLeaves(Long managerId);

  TeacherWithLeavesAndLessonsResponse getTeacherWithLeavesAndLessons(Long teacherId);

}
