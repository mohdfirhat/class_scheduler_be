package com.tfip.lessonscheduler.controller;

import java.time.LocalDate;
import java.util.List;

import com.tfip.lessonscheduler.dto.*;
import com.tfip.lessonscheduler.entity.Teacher;
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

  /**
   * Endpoint to get all Teachers <br/>
   * Endpoint: {@code http://localhost:8080/api/teachers} <br/>
   * Method: {@code GET} <br/>
   *
   * @return ResponseEntity with the list of Teachers
   */
  @GetMapping
  public ResponseEntity<List<Teacher>> getAllTeachers() {
    return new ResponseEntity<>(teacherService.getAllTeachers(),
      HttpStatus.OK);
  }

  /**
   * Endpoint to get all Teachers with Courses and Department under a manager (based on manager id) <br/>
   * Endpoint: {@code http://localhost:8080/api/teachers/1/courses} <br/>
   * Method: {@code GET} <br/>
   *
   * @return ResponseEntity with the list of Teachers
   */
  @GetMapping("{managerId}/courses")
  public ResponseEntity<List<TeacherWCoursesAndDepartmentResponse>> getTeachersWithCoursesAndDepartment(@PathVariable Long managerId) {
      return new ResponseEntity<>(teacherService.getTeachersWithCoursesAndDepartment(managerId), HttpStatus.OK);
  }

  /**
   * Endpoint to get all Teachers with leaves under a manager (based on manager id) <br/>
   * Endpoint: {@code http://localhost:8080/api/teachers/1/courses} <br/>
   * Method: {@code GET} <br/>
   *
   * @return ResponseEntity with the list of Teachers
   */
  @GetMapping("{managerId}/leaves")
  public ResponseEntity<List<TeacherWLeavesResponse>> getTeachersWithLeaves(@PathVariable Long managerId) {
    return new ResponseEntity<>(teacherService.getTeachersWithLeaves(managerId), HttpStatus.OK);
  }

  /**
   * Endpoint to get Teacher with leaves and sections <br/>
   * Endpoint: {@code http://localhost:8080/api/teachers/schedules/2} <br/>
   * Method: {@code GET} <br/>
   *
   * @return ResponseEntity with the list of Teachers
   */
  @GetMapping("schedules/{teacherId}")
  public ResponseEntity<TeacherWithLeavesAndSectionsResponse> getTeacherWithSchedules(@PathVariable Long teacherId) {
    return new ResponseEntity<>(teacherService.getTeacherWithLeavesAndSections(teacherId),
            HttpStatus.OK);
  }

  /**
   * Endpoint to get all Teachers who are available based on these criteria: <br/>
   * <ul>
   *   <li>available on a particular date and timeslot (no approved leave and confirmed sections)</li>
   *   <li>teacher teaches the particular course</li>
   * </ul>
   * Endpoint: {@code http://localhost:8080/api/teachers/1/available?date=2025-12-16&timeslotId=1&courseId=1} <br/>
   * Method: {@code GET} <br/>
   *
   * @return ResponseEntity with the list of Teachers
   */
  @GetMapping("{managerId}/available")
  public ResponseEntity<List<TeacherDto>> getAvailableTeacherAtTimeslot(@PathVariable Long managerId,
                                                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                                                        @RequestParam Long timeslotId,
                                                                        @RequestParam Long courseId) {
    return new ResponseEntity<>(teacherService.getAvailableTeacherAtTimeslotByManagerId(managerId,date,timeslotId,courseId),
      HttpStatus.OK);
  }
}
