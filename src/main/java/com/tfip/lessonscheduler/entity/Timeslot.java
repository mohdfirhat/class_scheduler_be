package com.tfip.lessonscheduler.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "timeslot")
public class Timeslot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "start_time",nullable=false,unique=true)
    private LocalTime startTime;

    @Column(name = "end_time",nullable=false,unique=true)
    private LocalTime endTime;
}

/*
    TODO:
     Create Timeslot DONE
     Fix query DONE
     Fix Dto
     Fix Mapper
     Fix Service

 */
