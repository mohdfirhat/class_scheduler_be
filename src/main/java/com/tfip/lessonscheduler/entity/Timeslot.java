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

import java.time.LocalTime;

/**
 * 'Timeslot' entity that maps to the "timeslot" table in the database. This
 * entity includes the id, start and end time of the timeslot.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "timeslot")
public class Timeslot {

    /**
     * The id number of the timeslot, mapped to the "id" column in the database.
     * Also used as the primary key in the "timeslot" table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * The start time of the timeslot, mapped to the "start_time" column in the
     * database
     */
    @Column(name = "start_time", nullable = false, unique = true)
    private LocalTime startTime;

    /**
     * The end time of the timeslot, mapped to the "end_time" column in the
     * database
     */
    @Column(name = "end_time", nullable = false, unique = true)
    private LocalTime endTime;
}
