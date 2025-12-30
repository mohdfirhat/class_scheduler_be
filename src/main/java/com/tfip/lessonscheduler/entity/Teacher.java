package com.tfip.lessonscheduler.entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

/**
 * 'Teacher' entity that maps to the "teacher" table in the database. This
 * entity includes the teacher id, first and last name, email, leave days,
 * avatar, manager, teachers (for managers), sections, teacher leaves,
 * department and course. For teachers who are managers, it is mapped to the
 * {@code Teacher} entities whom they manage in a many-to-one relationship. For
 * teachers who are not managers, it is mapped to {@code Teacher} entity that is
 * their manager in a one-to-many relationship. This entity is also mapped to
 * the {@code Section} and {@code TeacherLeave} in a one-to many relationship,
 * and {@code Department} to a many-to-one relationship and {@code Course} in a
 * many-to-many relationship.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "teacher")
public class Teacher {

    /**
     * The id number of the teacher, mapped to the "id" column in the database.
     * Also used as the primary key in the "teacher" table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * The first name of the teacher, mapped to the "first_name" column in the
     * database
     */
    @Column(name = "first_name", nullable = false)
    private String firstName;

    /**
     * The last name of the teacher, mapped to the "last_name" column in the
     * database
     */
    @Column(name = "last_name", nullable = false)
    private String lastName;

    /**
     * The email of the teacher, mapped to the "email" column in the database
     */
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    /**
     * The number of leave days the teacher has mapped to the "leave_days"
     * column in the database
     */
    @Column(name = "leave_days", nullable = false, columnDefinition = "INT " +
            "DEFAULT 28")
    private Integer leaveDays = 28;

    /**
     * The avatar URL of the teacher, mapped to the "avatar_url" column in the
     * database
     */
    @Column(name = "avatar_url")
    private String avatar;

    /**
     * Represents the association between the current {@code Teacher} entity and
     * the {@code Teacher} entity that represents his/her manager. This object
     * establishes a many-to-one relationship between the {@code Teacher} and
     * {@code Teacher} entities.
     */
    @JsonIgnoreProperties({"manager", "teachers", "sections", "teacherLeaves"
            , "department", "courses"})
    @ManyToOne
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    private Teacher manager;

    /**
     * Represents the association between the current {@code Teacher} entity who
     * is a manager and the set of {@code Teacher} entities that represents
     * his/her subordinates. This object establishes a one-to-many relationship
     * between the {@code Teacher} and {@code Teacher} entities.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "manager")
    private Set<Teacher> teachers;

    /**
     * Represents the association between the current {@code Teacher} entity and
     * the set of {@code Section} entities that the teacher is teaching. This
     * object establishes a one-to-many relationship between the {@code Teacher}
     * and {@code Section} entities.
     */
    @JsonIgnoreProperties("teacher")
    @OneToMany(mappedBy = "teacher")
    private Set<Section> sections;

    /**
     * Represents the association between the current {@code Teacher} entity and
     * the set of {@code TeacherLeave} entities that the teacher has. This
     * object establishes a one-to-many relationship between the {@code Teacher}
     * and {@code TeacherLeave} entities.
     */
    @JsonIgnoreProperties("teacher")
    @OneToMany(mappedBy = "teacher")
    private Set<TeacherLeave> teacherLeaves;

    /**
     * Represents the association between the current {@code Teacher} entity and
     * the {@code Department} entity that the teacher is enrolled in. This
     * object establishes a many-to-one relationship between the {@code Teacher}
     * and {@code Department} entities.
     */
    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id",
            nullable = false)
    private Department department;

    /**
     * Represents the association between the current {@code Teacher} entity and
     * the set of {@code Course} entities that the teacher is able to conduct.
     * This object establishes a many-to-many relationship between the
     * {@code Teacher} and {@code Course} entities.
     */
    @ManyToMany
    @JoinTable(
            name = "teachers_courses",
            joinColumns = {@JoinColumn(name = "teacher_id")},
            inverseJoinColumns = {@JoinColumn(name = "course_id")}
    )
    private Set<Course> courses;
}