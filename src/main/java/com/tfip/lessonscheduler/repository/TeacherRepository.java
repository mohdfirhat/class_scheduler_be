package com.tfip.lessonscheduler.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfip.lessonscheduler.entity.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
  List<Teacher> findAllByManagerId(Long managerId);
}
