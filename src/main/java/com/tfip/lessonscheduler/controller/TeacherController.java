package com.tfip.lessonscheduler.controller;

import java.util.List;

import com.tfip.lessonscheduler.dto.TeacherWLeavesResponse;
import com.tfip.lessonscheduler.dto.TeacherWCoursesAndDepartmentResponse;
import com.tfip.lessonscheduler.dto.TeacherWithLeavesAndSectionsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfip.lessonscheduler.service.TeacherService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("api/teachers")
public class TeacherController {

  private TeacherService teacherService;

  public TeacherController(TeacherService teacherService) {
    this.teacherService= teacherService;
  }

  @GetMapping("{managerId}/courses")
  public ResponseEntity<List<TeacherWCoursesAndDepartmentResponse>> getTeachersWithCoursesAndDepartment(@PathVariable Long managerId) {
      return new ResponseEntity<>(teacherService.getTeachersWithCoursesAndDepartment(managerId), HttpStatus.OK);
  }

  @GetMapping("{managerId}/leaves")
  public ResponseEntity<List<TeacherWLeavesResponse>> getTeachersWithLeaves(@PathVariable Long managerId) {
    return new ResponseEntity<>(teacherService.getTeachersWithLeaves(managerId), HttpStatus.OK);
  }

  @GetMapping("schedules/{teacherId}")
  public ResponseEntity<TeacherWithLeavesAndSectionsResponse> getTeacherWithSchedules(@PathVariable Long teacherId) {
    return new ResponseEntity<>(teacherService.getTeacherWithLeavesAndSections(teacherId),
            HttpStatus.OK);
  }
}
