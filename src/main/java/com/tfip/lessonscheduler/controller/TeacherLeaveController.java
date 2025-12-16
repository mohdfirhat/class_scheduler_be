package com.tfip.lessonscheduler.controller;

import com.tfip.lessonscheduler.dto.TeacherLeaveWConflictingSectionsResponse;
import com.tfip.lessonscheduler.dto.TeacherLeaveWTeacherResponse;
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

    @GetMapping("{leaveId}/teachers")
    public ResponseEntity<TeacherLeaveWTeacherResponse> getByIdWTeacher(@PathVariable Long leaveId) {
        return new ResponseEntity<>(teacherLeaveService.getByIdWTeacher(leaveId),
                HttpStatus.OK);
    }

    @GetMapping("conflict_leave/{leaveId}/all_leaves")
    public ResponseEntity<List<TeacherLeaveWTeacherResponse>> getLeavesOfAllTeachersInvolved(@PathVariable Long leaveId) {
        return new ResponseEntity<>(teacherLeaveService.getLeavesOfAllTeachersInvolved(leaveId),
                HttpStatus.OK);
    }

    @GetMapping("non_pending")
    public ResponseEntity<List<TeacherLeave>> getLeavesWithNonPendingStatus(){
        return new ResponseEntity<>(teacherLeaveService.getLeavesWithNonPendingStatus(),
                HttpStatus.OK);
    }

    @GetMapping("pending")
    public ResponseEntity<List<TeacherLeave>> getLeavesWithPendingStatus(){
        return new ResponseEntity<>(teacherLeaveService.getLeavesWithPendingStatus(),
                HttpStatus.OK);
    }

    @GetMapping("pending/conflicting")
    public ResponseEntity<List<TeacherLeaveWConflictingSectionsResponse>> getPendingLeavesWithConflict(){
        return new ResponseEntity<>(teacherLeaveService.getConflictingLeavesWithAffectedSections(),
                HttpStatus.OK);
    }

    @GetMapping("pending/non_conflicting")
    public ResponseEntity<List<TeacherLeave>> getPendingLeavesWithoutConflict(){
        return new ResponseEntity<>(teacherLeaveService.getNonConflictingLeavesWithPendingStatus(),
                HttpStatus.OK);
    }

    @PutMapping("reject/{leaveId}")
    public ResponseEntity<LeaveUpdatingDetails> rejectLeave(@PathVariable Long leaveId){
        return new ResponseEntity<>(teacherLeaveService.rejectLeave(leaveId),
                HttpStatus.OK);
    }

    @PutMapping("approve/{leaveId}")
    public ResponseEntity<LeaveUpdatingDetails> approveLeave(@PathVariable Long leaveId){
        return new ResponseEntity<>(teacherLeaveService.approveLeave(leaveId),
                HttpStatus.OK);
    }

}
