package com.tfip.lessonscheduler.service;

import java.util.List;

import com.tfip.lessonscheduler.dto.TeacherWSubjectResponse;

public interface TeacherService {
  public List<TeacherWSubjectResponse> getTeachers(Long managerId);

}
