package com.tfip.lessonscheduler.service;

import java.time.LocalDate;
import java.util.List;

import com.tfip.lessonscheduler.dto.*;
import com.tfip.lessonscheduler.entity.Teacher;

public interface TeacherService {

  List<Teacher> getAllTeachers();

  List<TeacherWCoursesAndDepartmentResponse> getTeachersWithCoursesAndDepartment(Long managerId);

  List<TeacherWLeavesResponse> getTeachersWithLeaves(Long managerId);

  TeacherWithLeavesAndSectionsResponse getTeacherWithLeavesAndSections(Long teacherId);

  List<TeacherDto> getAvailableTeacherAtTimeslotByManagerId(Long managerId,
                                                            LocalDate date,
                                                            Long timeslotId,
                                                            Long courseId);

}
