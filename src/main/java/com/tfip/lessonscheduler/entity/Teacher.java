package com.tfip.lessonscheduler.entity;

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
@Table(name = "teacher")
public class Teacher {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "first_name",nullable=false)
  private String firstName;

  @Column(name = "last_name",nullable=false)
  private String lastName;

  @Column(name = "email")
  private String email;

  @Column(name = "leave_days",nullable=false,columnDefinition="INT DEFAULT 14")
  @Builder.Default
  private Integer leaveDays = 14;

  @Column(name = "occupancy",nullable=false)
  private Integer occupancy;

  //add manager id
  //add department relationship
}