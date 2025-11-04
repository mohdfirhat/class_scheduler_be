package com.tfip.lessonscheduler.service;

import java.util.List;

import com.tfip.lessonscheduler.dto.TeacherWLeaveResponse;
import com.tfip.lessonscheduler.dto.TeacherWSubjectResponse;
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
  public List<TeacherWSubjectResponse> getTeachersWithSubjects(Long managerId) {
    return teacherRepository.findAllByManagerId(managerId).stream().map(teacherMapper::toTeacherWSubjectResponse).toList();
  }

  @Override
  public List<TeacherWLeaveResponse> getTeachersWithLeaves(Long managerId) {
    return teacherRepository.findAllByManagerId(managerId).stream().map(teacherMapper::toTeacherWLeaveResponse).toList();
  }

}