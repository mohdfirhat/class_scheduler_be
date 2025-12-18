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
 * 'TeacherLeave' entity that maps to the "teacher_leave" table in the database.
 * This entity includes the course id, start and end date, status and teacher
 * taking the leave. It is mapped to the {@code TeacherLeaveStatus} and
 * {@code Teacher} entities in a many-to-one relationship.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "teacher_leave")
public class TeacherLeave {

    /**
     * The id number of the teacher leave, mapped to the "id" column in the
     * database. Also used as the primary key in the "teacher_leave" table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * The start date of the leave, mapped to the "start_date" column in the
     * database
     */
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    /**
     * The end date of the leave, mapped to the "end_date" column in the
     * database
     */
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    /**
     * Represents the association between the current {@code TeacherLeave}
     * entity and the {@code TeacherLeaveStatus} entity. This object establishes
     * a many-to-one relationship between the {@code TeacherLeave} and
     * {@code TeacherLeaveStatus} entities.
     */
    @ManyToOne
    @JoinColumn(name = "teacher_leave_status_id", referencedColumnName = "id"
            , nullable = false)
    private TeacherLeaveStatus status;

    /**
     * Represents the association between the current {@code TeacherLeave}
     * entity and the {@code Teacher} entity. This object establishes a
     * many-to-one relationship between the {@code TeacherLeave} and
     * {@code Teacher} entities.
     */
    @JsonIgnoreProperties({"teacherLeaves", "sections", "courses"})
    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "id", nullable =
            false)
    private Teacher teacher;
}