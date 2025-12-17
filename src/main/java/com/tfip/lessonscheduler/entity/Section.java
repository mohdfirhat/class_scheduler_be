package com.tfip.lessonscheduler.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "section")
public class Section {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "remark",length=1000)
  private String remark;

  @Column(name = "date",nullable=false)
  private LocalDate date;

  @Column(name = "class_size",nullable=false)
  private Integer classSize;

  //NEED TO IMPLEMENT DEFAULT VALUE pending on INSERT Section
  @ManyToOne
  @JoinColumn(name = "section_status_id", referencedColumnName = "id",
          nullable=false)
  private SectionStatus status;

  @ManyToOne
  @JoinColumn(name = "timeslot_id", referencedColumnName = "id",
          nullable=false)
  private Timeslot timeslot;

  @JsonIgnoreProperties({"sections","teacherLeaves","courses"})
  @ManyToOne
  @JoinColumn(name = "teacher_id", referencedColumnName = "id",nullable=false)
  private Teacher teacher;

  @ManyToOne
  @JoinColumn(name = "venue_id", referencedColumnName = "id",nullable=false)
  private Venue venue;

  @ManyToOne
  @JoinColumn(name = "course_id", referencedColumnName = "id",nullable=false)
  private Course course;
}