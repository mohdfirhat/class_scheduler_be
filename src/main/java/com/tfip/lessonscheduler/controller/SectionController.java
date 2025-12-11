package com.tfip.lessonscheduler.controller;

import com.tfip.lessonscheduler.dto.SectionWCourseAndVenueAndTeacherResponse;
import com.tfip.lessonscheduler.dto.SectionWCourseAndAvailableTeachersResponse;
import com.tfip.lessonscheduler.entity.Section;
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

    private final SectionService sectionService;

    public  SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @GetMapping("conflict_leave/{leaveId}/available_teachers")
    public ResponseEntity<List<SectionWCourseAndAvailableTeachersResponse>> getSectionsWCourseAndAvailableTeachers(@PathVariable Long leaveId) {
        return new ResponseEntity<>(sectionService.getSectionsWithAvailableTeachers(leaveId), HttpStatus.OK);
    }

    @GetMapping("conflict_leave/{leaveId}/all_sections")
    public ResponseEntity<List<SectionWCourseAndVenueAndTeacherResponse>> getSectionsWCourseAndVenueOfAllTeachersInvolved(@PathVariable Long leaveId) {
        return new ResponseEntity<>(sectionService.getSectionsOfAllTeachersInvolved(leaveId), HttpStatus.OK);
    }

    @GetMapping("all")
    public ResponseEntity<List<Section>> getAllSections(){
        return new ResponseEntity<>(sectionService.getAllSections(),
                HttpStatus.OK);
    }

}
