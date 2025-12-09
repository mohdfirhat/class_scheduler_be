package com.tfip.lessonscheduler.service;

import java.time.LocalDate;
import java.util.List;

import com.tfip.lessonscheduler.dto.*;
import com.tfip.lessonscheduler.entity.Teacher;
import com.tfip.lessonscheduler.exception.ResourceNotFoundException;
import com.tfip.lessonscheduler.mapper.TeacherMapper;
import org.springframework.stereotype.Service;

import com.tfip.lessonscheduler.repository.TeacherRepository;

@Service
public class TeacherServiceImpl implements TeacherService{

  private TeacherRepository teacherRepository;
  private TeacherMapper teacherMapper;

  public TeacherServiceImpl(TeacherRepository teacherRepository,TeacherMapper teacherMapper) {
    this.teacherRepository = teacherRepository;
    this.teacherMapper = teacherMapper;
  }

  @Override
  public List<TeacherWCoursesAndDepartmentResponse> getTeachersWithCoursesAndDepartment(Long managerId) {
    return teacherRepository.findAllByManagerId(managerId).stream().map(teacherMapper::toTeacherWCourseAndDepartmentResponse).toList();
  }

  @Override
  public List<TeacherWLeavesResponse> getTeachersWithLeaves(Long managerId) {
    return teacherRepository.findAllByManagerId(managerId).stream().map(teacherMapper::toTeacherWLeaveResponse).toList();
  }

  @Override
  public TeacherWithLeavesAndSectionsResponse getTeacherWithLeavesAndSections(Long teacherId) {
    Teacher teacher =
      teacherRepository.findById(teacherId)
        .orElseThrow(()-> new ResourceNotFoundException(
          "Teacher with id " + teacherId + " not found"));
    return teacherMapper.toTeacherWLeavesAndSectionsResponse(teacher);
  }

  @Override
  public List<TeacherDto> getAvailableTeacherAtTimeslotByManagerId(Long managerId, LocalDate date,Long timeslotId) {
    List<Teacher> availableTeachers =
      teacherRepository.findAllAvailableTeacherAtTimeslotByManagerId(managerId,date,timeslotId);
    return availableTeachers.stream().map(teacherMapper::toTeacherDto).toList();
  }


}