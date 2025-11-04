package com.tfip.lessonscheduler.service;

import java.util.List;

import com.tfip.lessonscheduler.dto.TeacherWLeaveResponse;
import com.tfip.lessonscheduler.dto.TeacherWSubjectResponse;

public interface TeacherService {
  List<TeacherWSubjectResponse> getTeachersWithSubjects(Long managerId);

  List<TeacherWLeaveResponse> getTeachersWithLeaves(Long managerId);

}
