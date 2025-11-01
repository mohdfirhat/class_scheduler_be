package com.tfip.lessonscheduler.entity;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
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
@Table(name = "subject")
public class Subject {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "name",nullable=false)
  private String name;

  @Column(name = "subject_code", nullable = false, columnDefinition = "CHAR(5)")
  private String subjectCode;

  @ManyToOne(cascade={CascadeType.REMOVE})
  @JoinColumn(name = "department_id", referencedColumnName = "id",nullable=false)
  private Department department;

  @ManyToMany(mappedBy="subjects")
    Set<Teacher> teachers;

}