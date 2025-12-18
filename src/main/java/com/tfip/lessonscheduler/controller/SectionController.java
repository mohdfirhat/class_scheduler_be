package com.tfip.lessonscheduler.controller;

import com.tfip.lessonscheduler.dto.SectionCreateRequest;
import com.tfip.lessonscheduler.dto.SectionWCourseAndAvailableTeachersResponse;
import com.tfip.lessonscheduler.dto.SubTeacherRequest;
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

    /**
     * Endpoint to get all Sections with available teachers per Section within the applied leave (based on leaveId) <br/>
     * Endpoint: {@code http://localhost:8080/api/sections/conflict_leave/1
     * /available_teachers} <br/>
     * Method: {@code GET} <br/>
     *
     * @param leaveId the id of the leave querying
     * @return ResponseEntity with the list of Sections
     */
    @GetMapping("conflict_leave/{leaveId}/available_teachers")
    public ResponseEntity<List<SectionWCourseAndAvailableTeachersResponse>> getSectionsWCourseAndAvailableTeachers(@PathVariable Long leaveId) {
        return new ResponseEntity<>(sectionService.getSectionsWithAvailableTeachers(leaveId), HttpStatus.OK);
    }

    /**
     * Endpoint to get all Sections for all available teacher (including teacher applying for this leaveId)
     * who are involved in the leave conflict resolution <br/>
     * Endpoint: {@code http://localhost:8080/api/sections/conflict_leave/1
     * /all_sections} <br/>
     * Method: {@code GET} <br/>
     *
     * @param leaveId the id of the leave querying
     * @return ResponseEntity with the list of Sections
     */
    @GetMapping("conflict_leave/{leaveId}/all_sections")
    public ResponseEntity<List<Section>> getSectionsWCourseAndVenueOfAllTeachersInvolved(@PathVariable Long leaveId) {
        return new ResponseEntity<>(sectionService.getSectionsOfAllTeachersInvolved(leaveId), HttpStatus.OK);
    }

    /**
     * Endpoint to get all Sections <br/>
     * Endpoint: {@code http://localhost:8080/api/sections} <br/>
     * Method: {@code GET} <br/>
     *
     * @return ResponseEntity with the list of Sections
     */
    @GetMapping
    public ResponseEntity<List<Section>> getAllSections(){
        return new ResponseEntity<>(sectionService.getAllSections(),
                HttpStatus.OK);
    }

    /**
     * Endpoint to get Section by id <br/>
     * Endpoint: {@code http://localhost:8080/api/sections/1} <br/>
     * Method: {@code GET} <br/>
     *
     * @return ResponseEntity with Section
     */
    @GetMapping("{sectionId}")
    public ResponseEntity<Section> getSectionWCourseAndVenueAndTeacherById(@PathVariable Long sectionId) {
        return new ResponseEntity<>(sectionService.getSectionById(sectionId),
          HttpStatus.OK);
    }

    /**
     * Endpoint to create Section <br/>
     * Endpoint: {@code http://localhost:8080/api/sections} <br/>
     * Method: {@code POST} <br/>
     *
     * Expected JSON in HTTP body
     * <pre>
     *   {@code
     *      {
     *          "remark" : "New Lesson",
     *          "date" : "2025-12-19",
     *          "classSize" : 10,
     *          "timeslotId" : 1,
     *          "teacherId" : 3,
     *          "venueId" : 1,
     *          "courseId" : 1
     *      }
     *   }
     * </pre>
     */
    @PostMapping
    public void createSection(@RequestBody SectionCreateRequest sectionCreateRequest) {
        sectionService.saveSection(sectionCreateRequest);
    }

    /**
     * Endpoint to cancel Section by id <br/>
     * Endpoint: {@code http://localhost:8080/api/sections/cancel/1} <br/>
     * Method: {@code PUT}
     *
     * @return String with a success message
     */
    @PutMapping("cancel/{sectionId}")
    public ResponseEntity<String> cancelSection(@PathVariable Long sectionId){
        return new ResponseEntity<>(sectionService.cancelSection(sectionId)
                , HttpStatus.OK);
    }

    /**
     * Endpoint to substitute Teacher for the Section <br/>
     * Endpoint: {@code http://localhost:8080/api/sections/sub_teacher} <br/>
     * Method: {@code PUT}
     *
     * Expected JSON in HTTP body
     * <pre>
     *   {@code
     *      {
     *          "sectionId" : 1,
     *          "teacherId" : 2
     *      }
     *   }
     * </pre>
     */
    @PutMapping("sub_teacher")
    public void updateSectionTeacher(@RequestBody SubTeacherRequest subTeacherRequest) {
        sectionService.updateSectionTeacher(subTeacherRequest);
    }

    /**
     * Endpoint to approved Section by id <br/>
     * Endpoint: {@code http://localhost:8080/api/sections/approve/1} <br/>
     * Method: {@code PUT}
     *
     * @return String with a success message
     */
    @PutMapping("approve/{sectionId}")
    public ResponseEntity<String> approveSection(@PathVariable Long sectionId){
        return new ResponseEntity<>(sectionService.approveSection(sectionId)
                , HttpStatus.OK);
    }
}
