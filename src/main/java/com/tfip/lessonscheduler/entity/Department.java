package com.tfip.lessonscheduler.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 'Department' entity that maps to the "department" table in the database. This
 * entity includes the department id and name of the department.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "department")
public class Department {

    /**
     * The id number of the department, mapped to the "id" column in the
     * database. Also used as the primary key in the "department" table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * The name of the department, mapped to the "name" column in the database
     */
    @Column(name = "name", nullable = false, unique = true)
    private String name;

}
