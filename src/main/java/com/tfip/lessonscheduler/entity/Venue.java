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
 * 'Venue' entity that maps to the "venue" table in the database. This entity
 * includes the venue id, name, address, description, image URL and occupancy.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "venue")
public class Venue {

    /**
     * The id number of the venue, mapped to the "id" column in the database.
     * Also used as the primary key in the "venue" table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * The name of the venue, mapped to the "name" column in the database
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * The address of the venue, mapped to the "address" column in the database
     */
    @Column(name = "address", nullable = false)
    private String address;

    /**
     * The description of the venue, mapped to the "description" column in the
     * database
     */
    @Column(name = "description", length = 1000)
    private String description;

    /**
     * The image URL of the venue, mapped to the "img_url" column in the
     * database
     */
    @Column(name = "img_url")
    private String imgUrl;

    /**
     * The occupancy of the venue, mapped to the "occupancy" column in the
     * database
     */
    @Column(name = "occupancy", nullable = false)
    private Integer occupancy;
}