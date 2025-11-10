package com.tfip.lessonscheduler.controller;

import com.tfip.lessonscheduler.dto.TeacherLeaveWTeacherResponse;
import com.tfip.lessonscheduler.service.TeacherLeaveService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/leaves")
public class TeacherLeaveController {
    private TeacherLeaveService teacherLeaveService;

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


}
