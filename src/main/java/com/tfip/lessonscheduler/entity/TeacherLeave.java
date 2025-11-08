package com.tfip.lessonscheduler.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

//  @Column(name = "status",nullable=false,columnDefinition="VARCHAR(20) DEFAULT 'pending'")
//  private String status = "pending";
  @ManyToOne
  @JoinColumn(name = "teacher_leave_status_id", referencedColumnName = "id",nullable=false)
  private TeacherLeaveStatus status;


  @ManyToOne
  @JoinColumn(name = "teacher_id", referencedColumnName = "id",nullable=false)
  private Teacher teacher;
}