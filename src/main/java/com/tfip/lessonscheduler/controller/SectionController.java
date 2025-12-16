package com.tfip.lessonscheduler.controller;

import com.tfip.lessonscheduler.dto.SectionCreateDto;
import com.tfip.lessonscheduler.dto.SectionWCourseAndVenueAndTeacherResponse;
import com.tfip.lessonscheduler.dto.SectionWCourseAndAvailableTeachersResponse;
import com.tfip.lessonscheduler.entity.Section;
import com.tfip.lessonscheduler.service.SectionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("{sectionId}")
    public ResponseEntity<SectionWCourseAndVenueAndTeacherResponse> getSectionWCourseAndVenueAndTeacherById(@PathVariable Long sectionId) {
        return new ResponseEntity<>(sectionService.getSectionById(sectionId),
          HttpStatus.OK);
    }

    @PostMapping
    public void createSection(@RequestBody SectionCreateDto sectionCreateDto) {
        sectionService.saveSection(sectionCreateDto);
    }

    @PutMapping("cancel/{sectionId}")
    public ResponseEntity<String> cancelSection(@PathVariable Long sectionId){
        return new ResponseEntity<>(sectionService.cancelSection(sectionId)
                , HttpStatus.OK);
    }

    @PutMapping("approve/{sectionId}")
    public ResponseEntity<String> approveSection(@PathVariable Long sectionId){
        return new ResponseEntity<>(sectionService.approveSection(sectionId)
                , HttpStatus.OK);
    }
}
