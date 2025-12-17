package com.tfip.lessonscheduler.controller;

import com.tfip.lessonscheduler.dto.TeacherLeaveWConflictingSectionsResponse;
import com.tfip.lessonscheduler.entity.TeacherLeave;
import com.tfip.lessonscheduler.model.LeaveUpdatingDetails;
import com.tfip.lessonscheduler.service.TeacherLeaveService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/leaves")
public class TeacherLeaveController {
    private final TeacherLeaveService teacherLeaveService;

    public TeacherLeaveController(TeacherLeaveService teacherLeaveService) {
        this.teacherLeaveService = teacherLeaveService;
    }

    /**
     * Endpoint to get TeacherLeave with Teacher by leaveId <br/>
     * Endpoint: {@code http://localhost:8080/api/leaves/1/teachers} <br/>
     * Method: {@code GET} <br/>
     *
     * @return ResponseEntity with the TeacherLeave
     */
    @GetMapping("{leaveId}/teachers")
    public ResponseEntity<TeacherLeave> getByIdWTeacher(@PathVariable Long leaveId) {
        return new ResponseEntity<>(teacherLeaveService.getByIdWTeacher(leaveId),
                HttpStatus.OK);
    }

    /**
     * Endpoint to get all TeacherLeaves of all Teachers involved in the leave
     * resolution (inclusive of the Teacher taking the conflicting leave) <br/>
     * Endpoint: {@code http://localhost:8080/api/leaves/conflict_leave/1/all_leaves} <br/>
     * Method: {@code GET} <br/>
     *
     * @return ResponseEntity with a list of TeacherLeaves
     */
    @GetMapping("conflict_leave/{leaveId}/all_leaves")
    public ResponseEntity<List<TeacherLeave>> getLeavesOfAllTeachersInvolved(@PathVariable Long leaveId) {
        return new ResponseEntity<>(teacherLeaveService.getLeavesOfAllTeachersInvolved(leaveId),
                HttpStatus.OK);
    }

    /**
     * Endpoint to get TeacherLeave by status (non-pending) <br/>
     * Endpoint: {@code http://localhost:8080/api/leaves/non_pending} <br/>
     * Method: {@code GET} <br/>
     *
     * @return ResponseEntity with a list of TeacherLeaves
     */
    @GetMapping("non_pending")
    public ResponseEntity<List<TeacherLeave>> getLeavesWithNonPendingStatus(){
        return new ResponseEntity<>(teacherLeaveService.getLeavesWithNonPendingStatus(),
                HttpStatus.OK);
    }

    /**
     * Endpoint to get TeacherLeave by status (pending) <br/>
     * Endpoint: {@code http://localhost:8080/api/leaves/pending} <br/>
     * Method: {@code GET} <br/>
     *
     * @return ResponseEntity with a list of TeacherLeaves
     */
    @GetMapping("pending")
    public ResponseEntity<List<TeacherLeave>> getLeavesWithPendingStatus(){
        return new ResponseEntity<>(teacherLeaveService.getLeavesWithPendingStatus(),
                HttpStatus.OK);
    }

    /**
     * Endpoint to get conflicting TeacherLeave by status (pending) <br/>
     * Endpoint: {@code http://localhost:8080/api/leaves/pending/conflicting} <br/>
     * Method: {@code GET} <br/>
     *
     * @return ResponseEntity with a list of TeacherLeaves
     */
    @GetMapping("pending/conflicting")
    public ResponseEntity<List<TeacherLeaveWConflictingSectionsResponse>> getPendingLeavesWithConflict(){
        return new ResponseEntity<>(teacherLeaveService.getConflictingLeavesWithAffectedSections(),
                HttpStatus.OK);
    }

    /**
     * Endpoint to get non-conflicting TeacherLeave by status (pending) <br/>
     * Endpoint: {@code http://localhost:8080/api/leaves/pending/non_conflicting} <br/>
     * Method: {@code GET} <br/>
     *
     * @return ResponseEntity with a list of TeacherLeaves
     */
    @GetMapping("pending/non_conflicting")
    public ResponseEntity<List<TeacherLeave>> getPendingLeavesWithoutConflict(){
        return new ResponseEntity<>(teacherLeaveService.getNonConflictingLeavesWithPendingStatus(),
                HttpStatus.OK);
    }

    // TODO: Weilong change LeaveUpdatingDetails to String
    /**
     * Endpoint to update TeacherLeave status to reject <br/>
     * Endpoint: {@code http://localhost:8080/api/leaves/reject/1} <br/>
     * Method: {@code PUT} <br/>
     *
     * @return ResponseEntity with LeaveUpdatingDetails Object with a message
     */
    @PutMapping("reject/{leaveId}")
    public ResponseEntity<LeaveUpdatingDetails> rejectLeave(@PathVariable Long leaveId){
        return new ResponseEntity<>(teacherLeaveService.rejectLeave(leaveId),
                HttpStatus.OK);
    }

    // TODO: Weilong change LeaveUpdatingDetails to String
    /**
     * Endpoint to update TeacherLeave status to approve <br/>
     * Endpoint: {@code http://localhost:8080/api/leaves/reject/1} <br/>
     * Method: {@code PUT} <br/>
     *
     * @return ResponseEntity with LeaveUpdatingDetails Object with a message
     */
    @PutMapping("approve/{leaveId}")
    public ResponseEntity<LeaveUpdatingDetails> approveLeave(@PathVariable Long leaveId){
        return new ResponseEntity<>(teacherLeaveService.approveLeave(leaveId),
                HttpStatus.OK);
    }

}
