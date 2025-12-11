package com.tfip.lessonscheduler.entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

  @Column(name = "email",nullable=false)
  private String email;

  @Column(name = "leave_days",nullable=false,columnDefinition="INT DEFAULT 14")
  private Integer leaveDays = 14;

  @Column(name="avatar_url")
  private String avatar;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "manager_id", referencedColumnName = "id")
  private Teacher manager;

  @JsonIgnore
  @OneToMany(mappedBy = "manager")
  private Set<Teacher> teachers;

  @JsonIgnoreProperties("teacher")
  @OneToMany(mappedBy = "teacher")
  private Set<Section> sections;

  @OneToMany(mappedBy = "teacher")
  private Set<TeacherLeave> teacherLeaves;

  @ManyToOne
  @JoinColumn(name = "department_id", referencedColumnName = "id",nullable=false)
  private Department department;

  @ManyToMany
  @JoinTable(
    name = "teachers_courses", 
    joinColumns = { @JoinColumn(name = "teacher_id") }, 
    inverseJoinColumns = { @JoinColumn(name = "course_id") }
  )
  private Set<Course> courses;
}