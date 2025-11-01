package com.tfip.lessonscheduler.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "teacher_leave")
public class TeacherLeave {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "start_date",nullable=false)
  private LocalDate  startDate;

  @Column(name = "end_date",nullable=false)
  private LocalDate  endDate;

  @Column(name = "status",nullable=false,columnDefinition="VARCHAR(20) DEFAULT 'pending'")
  @Builder.Default
  private String status = "pending";

  //add teacher relationship
}