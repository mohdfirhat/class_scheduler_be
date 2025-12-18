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

/**
 * 'Section' entity that maps to the "section" table in the database. This
 * entity includes the section id, remarks, date, class size, status, time slot,
 * teacher, venue and course. It is mapped to the {@code SectionStatus},
 * {@code Timeslot}, {@code Teacher}, {@code Venue} and {@code Course} entities
 * in a many-to-one relationship.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "section")
public class Section {

    /**
     * The id number of the section, mapped to the "id" column in the database.
     * Also used as the primary key in the "section" table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Brief remarks of the section (E.g., class test, pop quiz) mapped to the
     * "remark" column in the database
     */
    @Column(name = "remark", length = 1000)
    private String remark;

    /**
     * The date of the section, mapped to the "date" column in the database
     */
    @Column(name = "date", nullable = false)
    private LocalDate date;

    /**
     * The class size of the section, mapped to the "class_size" column in the
     * database
     */
    @Column(name = "class_size", nullable = false)
    private Integer classSize;

    /**
     * Represents the association between the current {@code Section} entity and
     * the {@code SectionStatus} entity. This object establishes a many-to-one
     * relationship between the {@code Section} and {@code SectionStatus}
     * entities.
     */
    @ManyToOne
    @JoinColumn(name = "section_status_id", referencedColumnName = "id",
            nullable = false)
    private SectionStatus status;

    /**
     * Represents the association between the current {@code Section} entity and
     * the {@code Timeslot} entity. This object establishes a many-to-one
     * relationship between the {@code Section} and {@code Timeslot} entities.
     */
    @ManyToOne
    @JoinColumn(name = "timeslot_id", referencedColumnName = "id",
            nullable = false)
    private Timeslot timeslot;

    /**
     * Represents the association between the current {@code Section} entity and
     * the {@code Teacher} entity. This object establishes a many-to-one
     * relationship between the {@code Section} and {@code Teacher} entities.
     */
    @JsonIgnoreProperties({"sections", "teacherLeaves", "courses"})
    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "id", nullable =
            false)
    private Teacher teacher;

    /**
     * Represents the association between the current {@code Section} entity and
     * the {@code Venue} entity. This object establishes a many-to-one
     * relationship between the {@code Section} and {@code Venue} entities.
     */
    @ManyToOne
    @JoinColumn(name = "venue_id", referencedColumnName = "id", nullable =
            false)
    private Venue venue;

    /**
     * Represents the association between the current {@code Section} entity and
     * the {@code Course} entity. This object establishes a many-to-one
     * relationship between the {@code Section} and {@code Course} entities.
     */
    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id", nullable =
            false)
    private Course course;
}