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
 * 'SectionStatus' entity that maps to the "section_status" table in the
 * database. This entity includes the status id and type of status. Default
 * statuses in the database are "pending", "approved" and "rejected".
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "section_status")
public class SectionStatus {

    /**
     * The id number of the section status, mapped to the "id" column in the
     * database. Also used as the primary key in the "section_status" table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * The type of status, mapped to the "type" column in the database
     */
    @Column(name = "type", nullable = false, length = 20, unique = true)
    private String type;

}
