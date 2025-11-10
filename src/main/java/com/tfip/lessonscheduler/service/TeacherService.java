package com.tfip.lessonscheduler.service;

import java.util.List;

import com.tfip.lessonscheduler.dto.TeacherWLeavesResponse;
import com.tfip.lessonscheduler.dto.TeacherWCoursesAndDepartmentResponse;
import com.tfip.lessonscheduler.dto.TeacherWithLeavesAndSectionsResponse;

public interface TeacherService {
  List<TeacherWCoursesAndDepartmentResponse> getTeachersWithCoursesAndDepartment(Long managerId);

  List<TeacherWLeavesResponse> getTeachersWithLeaves(Long managerId);

  TeacherWithLeavesAndSectionsResponse getTeacherWithLeavesAndSections(Long teacherId);

}
