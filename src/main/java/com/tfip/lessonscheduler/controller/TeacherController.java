package com.tfip.lessonscheduler.controller;

import java.time.LocalDate;
import java.util.List;

import com.tfip.lessonscheduler.dto.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tfip.lessonscheduler.service.TeacherService;

@RestController
@RequestMapping("api/teachers")
public class TeacherController {

  private final TeacherService teacherService;

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
  @GetMapping("{managerId}/available")
  public ResponseEntity<List<TeacherDto>> getAvailableTeacherAtTimeslot(@PathVariable Long managerId,
                                                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                                                        @RequestParam Long timeslotId,
                                                                        @RequestParam Long courseId) {
    return new ResponseEntity<>(teacherService.getAvailableTeacherAtTimeslotByManagerId(managerId,date,timeslotId,courseId),
      HttpStatus.OK);
  }
}
