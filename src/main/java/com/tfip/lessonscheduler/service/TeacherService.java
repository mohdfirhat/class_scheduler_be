package com.tfip.lessonscheduler.service;

import java.time.LocalDate;
import java.util.List;

import com.tfip.lessonscheduler.entity.Teacher;

public interface TeacherService {

  List<Teacher> getAllTeachers();

  List<Teacher> getTeachersWithCoursesAndDepartment(Long managerId);

  List<Teacher> getTeachersWithLeaves(Long managerId);

  Teacher getTeacherWithLeavesAndSections(Long teacherId);

  List<Teacher> getAvailableTeacherAtTimeslotByManagerId(Long managerId,
                                                            LocalDate date,
                                                            Long timeslotId,
                                                            Long courseId);

}
