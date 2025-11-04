package com.tfip.lessonscheduler.service;

import java.util.List;

import com.tfip.lessonscheduler.dto.TeacherWLeavesResponse;
import com.tfip.lessonscheduler.dto.TeacherWSubjectsResponse;
import com.tfip.lessonscheduler.dto.TeacherWithLeavesAndLessonsResponse;
import com.tfip.lessonscheduler.entity.Teacher;
import com.tfip.lessonscheduler.exception.AppException;
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
  public List<TeacherWSubjectsResponse> getTeachersWithSubjects(Long managerId) {
    return teacherRepository.findAllByManagerId(managerId).stream().map(teacherMapper::toTeacherWSubjectResponse).toList();
  }

  @Override
  public List<TeacherWLeavesResponse> getTeachersWithLeaves(Long managerId) {
    return teacherRepository.findAllByManagerId(managerId).stream().map(teacherMapper::toTeacherWLeaveResponse).toList();
  }

  @Override
  public TeacherWithLeavesAndLessonsResponse getTeacherWithLeavesAndLessons(Long teacherId) {
    Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(()-> new AppException("Teacher with id " + teacherId + " not found"));
    return teacherMapper.toTeacherWLeavesAndLessonsResponse(teacher);
  }



}