package com.tfip.lessonscheduler.entity;

import java.time.LocalDateTime;

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
@Table(name = "lesson")
public class Lesson {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "name",nullable=false)
  private String name;

  @Column(name = "description",length=1000)
  private String description;

  @Column(name = "start_time",nullable=false,precision=0)
  private LocalDateTime startTime;

  @Column(name = "end_time",nullable=false,precision=0)
  private LocalDateTime endTime;

  @Column(name = "class_size",nullable=false)
  private Integer classSize;

  @Column(name = "status",nullable=false,columnDefinition="VARCHAR(20) DEFAULT 'pending'")
  private String status = "pending";

  //add teacher relationship
  //add venue relationship
  //add subject relationship
  @ManyToOne
  @JoinColumn(name = "teacher_id", referencedColumnName = "id",nullable=false)
  private Teacher teacher;

  @ManyToOne
  @JoinColumn(name = "venue_id", referencedColumnName = "id",nullable=false)
  private Venue venue;

  @ManyToOne
  @JoinColumn(name = "subject_id", referencedColumnName = "id",nullable=false)
  private Subject subject;
}