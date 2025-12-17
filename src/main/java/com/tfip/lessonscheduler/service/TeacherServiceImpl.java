package com.tfip.lessonscheduler.service;

import java.time.LocalDate;
import java.util.List;

import com.tfip.lessonscheduler.entity.Teacher;
import com.tfip.lessonscheduler.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.tfip.lessonscheduler.repository.TeacherRepository;

@Service
public class TeacherServiceImpl implements TeacherService{

  private final TeacherRepository teacherRepository;

  public TeacherServiceImpl(TeacherRepository teacherRepository) {
    this.teacherRepository = teacherRepository;
  }

  @Override
  public List<Teacher> getAllTeachers() {
    return teacherRepository.findAll();
  }

  //TODO: remove one of them
  @Override
  public List<Teacher> getTeachersWithCoursesAndDepartment(Long managerId) {
//    return teacherRepository.findAllByManagerId(managerId).stream().map(teacherMapper::toTeacherWCourseAndDepartmentResponse).toList();
    return teacherRepository.findAllByManagerId(managerId);
  }

  //TODO: remove one of them
  @Override
  public List<Teacher> getTeachersWithLeaves(Long managerId) {
//    return teacherRepository.findAllByManagerId(managerId).stream().map(teacherMapper::toTeacherWLeaveResponse).toList();
    return teacherRepository.findAllByManagerId(managerId);
  }

  //TODO: remove one of them
  @Override
  public Teacher getTeacherWithLeavesAndSections(Long teacherId) {
    Teacher teacher =
      teacherRepository.findById(teacherId)
        .orElseThrow(()-> new ResourceNotFoundException(
          "Teacher with id " + teacherId + " not found"));
    return teacher;
  }

  @Override
  public List<Teacher> getAvailableTeacherAtTimeslotByManagerId(Long managerId, LocalDate date,Long timeslotId,Long  courseId) {
    List<Teacher> availableTeachers =
      teacherRepository.findAllAvailableTeacherAtTimeslotByManagerId(managerId,date,timeslotId,courseId);
//    return availableTeachers.stream().map(teacherMapper::toTeacherDto).toList();
    return availableTeachers;
  }

}