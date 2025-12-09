package com.tfip.lessonscheduler.service;

import java.time.LocalDate;
import java.util.List;

import com.tfip.lessonscheduler.dto.*;

public interface TeacherService {
  List<TeacherWCoursesAndDepartmentResponse> getTeachersWithCoursesAndDepartment(Long managerId);

  List<TeacherWLeavesResponse> getTeachersWithLeaves(Long managerId);

  TeacherWithLeavesAndSectionsResponse getTeacherWithLeavesAndSections(Long teacherId);

  List<TeacherDto> getAvailableTeacherAtTimeslotByManagerId(Long managerId,
                                                            LocalDate date,
                                                            Long timeslotId);

}
