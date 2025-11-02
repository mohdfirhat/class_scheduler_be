package com.tfip.lessonscheduler.controller;

import java.util.List;

import com.tfip.lessonscheduler.dto.TeacherWSubjectResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfip.lessonscheduler.service.TeacherService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.tfip.lessonscheduler.entity.Teacher;


@RestController
@RequestMapping("api/teachers")
public class TeacherController {

  private TeacherService teacherService;

  public TeacherController(TeacherService teacherService) {
    this.teacherService= teacherService;
  }

  @GetMapping("/{managerId}")
  public ResponseEntity<List<TeacherWSubjectResponse>> getAllTeacher(@PathVariable Long managerId) {
      return new ResponseEntity<List<TeacherWSubjectResponse>>(teacherService.getTeachers(managerId),HttpStatus.OK);
  }
  
}
