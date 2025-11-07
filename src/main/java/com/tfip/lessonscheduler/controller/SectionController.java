package com.tfip.lessonscheduler.controller;

import com.tfip.lessonscheduler.dto.SectionWTeacherResponse;
import com.tfip.lessonscheduler.dto.SectionWAvailableTeachersResponse;
import com.tfip.lessonscheduler.service.SectionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/sections")
public class SectionController {

    private SectionService lessonService;

    public  SectionController(SectionService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping("conflict_leave/{leaveId}/available_teachers")
    public ResponseEntity<List<SectionWAvailableTeachersResponse>> getSectionsWithAvailableTeachers(@PathVariable Long leaveId) {
        return new ResponseEntity<>(lessonService.getAllSectionsWithAvailableTeachers(leaveId), HttpStatus.OK);
    }

    @GetMapping("conflict_leave/{leaveId}/all_sections")
    public ResponseEntity<List<SectionWTeacherResponse>> getAllSectionsOfAllTeachersInvolved(@PathVariable Long leaveId) {
        return new ResponseEntity<>(lessonService.getAllSectionsOfAllTeachersInvolved(leaveId), HttpStatus.OK);
    }

}
