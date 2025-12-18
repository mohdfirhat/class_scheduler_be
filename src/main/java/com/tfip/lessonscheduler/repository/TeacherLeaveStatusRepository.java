package com.tfip.lessonscheduler.repository;

import com.tfip.lessonscheduler.entity.TeacherLeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing TeacherLeaveStatus entities. Extends
 * JpaRepository to provide basic CRUD operations.
 */
public interface TeacherLeaveStatusRepository extends
        JpaRepository<TeacherLeaveStatus, Long> {

    /**
     * Retrieves a TeacherLeaveStatus entity based on the specified type.
     *
     * @param type the type of the leave status to be retrieved, such as
     *             "pending", "approved", or "rejected"
     * @return a TeacherLeaveStatus entity that matches the provided type;
     * returns null if no matching entity is found
     */
    TeacherLeaveStatus findByType(String type);
}
