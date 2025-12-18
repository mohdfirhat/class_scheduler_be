package com.tfip.lessonscheduler.entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

/**
 * 'Course' entity that maps to the "course" table in the database. This entity
 * includes the course id, name, course code and a description of the course. It
 * is mapped to the {@code Department} and {@code Teacher} entities in a
 * many-to-one and many-to-many relationship respectively.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "course")
public class Course {

    /**
     * The id number of the course, mapped to the "id" column in the database.
     * Also used as the primary key in the "course" table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * The name of the course, mapped to the "name" column in the database
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * The course code of the course, mapped to the "course_code" column in the
     * database
     */
    @Column(name = "course_code", nullable = false, unique = true,
            columnDefinition = "CHAR(5)")
    private String courseCode;

    /**
     * A short description of the course, mapped to the "description" column in
     * the database
     */
    @Column(name = "description", length = 1000)
    private String description;

    /**
     * Represents the association between the current {@code Course} entity and
     * the {@code Department} entity. This object establishes a many-to-one
     * relationship between the {@code Course} and {@code Department} entities.
     */
    @ManyToOne(cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "department_id", referencedColumnName = "id",
            nullable = false)
    private Department department;

    /**
     * Set of {@code Teacher} entities associated with a {@code Course}. This
     * object establishes a many-to-many relationship between the {@code Course}
     * entity and the {@code Teacher} entity.
     */
    @ManyToMany(mappedBy = "courses")
    @JsonIgnore
    Set<Teacher> teachers;

}