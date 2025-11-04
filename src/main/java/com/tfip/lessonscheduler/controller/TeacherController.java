package com.tfip.lessonscheduler.controller;

import java.util.List;

import com.tfip.lessonscheduler.dto.TeacherWLeaveResponse;
import com.tfip.lessonscheduler.dto.TeacherWSubjectResponse;
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

  @GetMapping("subjects/{managerId}")
  public ResponseEntity<List<TeacherWSubjectResponse>> getAllTeacherWithSubjects(@PathVariable Long managerId) {
      return new ResponseEntity<>(teacherService.getTeachersWithSubjects(managerId), HttpStatus.OK);
  }

  @GetMapping("leaves/{managerId}")
  public ResponseEntity<List<TeacherWLeaveResponse>> getAllTeacherWithLeaves(@PathVariable Long managerId) {
    return new ResponseEntity<>(teacherService.getTeachersWithLeaves(managerId), HttpStatus.OK);
  }
  
}
