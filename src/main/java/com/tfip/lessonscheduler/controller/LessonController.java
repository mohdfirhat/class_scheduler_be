package com.tfip.lessonscheduler.controller;

import com.tfip.lessonscheduler.dto.LessonWTeacherResponse;
import com.tfip.lessonscheduler.dto.LessonWAvailableTeachersResponse;
import com.tfip.lessonscheduler.service.LessonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/lessons")
public class LessonController {

    private LessonService lessonService;

    public  LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping("conflict_leave/{leaveId}/available_teachers")
    public ResponseEntity<List<LessonWAvailableTeachersResponse>> getLessonsWithAvailableTeachers(@PathVariable Long leaveId) {
        return new ResponseEntity<>(lessonService.getAllLessonsWithAvailableTeachers(leaveId), HttpStatus.OK);
    }

    @GetMapping("conflict_leave/{leaveId}/all_lessons")
    public ResponseEntity<List<LessonWTeacherResponse>> getAllLessonsOfAllTeachersInvolved(@PathVariable Long leaveId) {
        return new ResponseEntity<>(lessonService.getAllLessonsOfAllTeachersInvolved(leaveId), HttpStatus.OK);
    }

}
