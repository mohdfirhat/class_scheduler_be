package com.tfip.lessonscheduler.service;

import com.tfip.lessonscheduler.dto.LessonWTeacherResponse;
import com.tfip.lessonscheduler.dto.LessonWAvailableTeachersResponse;
import com.tfip.lessonscheduler.dto.TeacherDto;
import com.tfip.lessonscheduler.entity.Lesson;
import com.tfip.lessonscheduler.entity.Teacher;
import com.tfip.lessonscheduler.entity.TeacherLeave;
import com.tfip.lessonscheduler.exception.AppException;
import com.tfip.lessonscheduler.mapper.LessonMapper;
import com.tfip.lessonscheduler.mapper.TeacherMapper;
import com.tfip.lessonscheduler.repository.LessonRepository;
import com.tfip.lessonscheduler.repository.TeacherLeaveRepository;
import com.tfip.lessonscheduler.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class LessonServiceImpl implements LessonService {

    private LessonRepository lessonRepository;
    private TeacherLeaveRepository teacherLeaveRepository;
    private TeacherRepository teacherRepository;
    private TeacherMapper teacherMapper;
    private LessonMapper lessonMapper;

    public LessonServiceImpl(LessonRepository lessonRepository,TeacherLeaveRepository teacherLeaveRepository,TeacherRepository teacherRepository, TeacherMapper teacherMapper, LessonMapper lessonMapper) {
        this.lessonRepository = lessonRepository;
        this.teacherLeaveRepository = teacherLeaveRepository;
        this.teacherRepository = teacherRepository;
        this.teacherMapper = teacherMapper;
        this.lessonMapper = lessonMapper;
    }


    @Override
    public List<LessonWAvailableTeachersResponse> getAllLessonsWithAvailableTeachers(Long leaveId) {

        // Get specific leave to resolve leave-lesson conflict
        TeacherLeave leave = teacherLeaveRepository.findById(leaveId)
                .orElseThrow(() -> new AppException("Leave with id " + leaveId + " not found"));

        LocalDateTime start = leave.getStartDate().atStartOfDay();
        LocalDateTime end = leave.getEndDate().atTime(23, 59, 59);

        // Get all lesson within leave period
        List<Lesson> conflictingLessons =
                lessonRepository.findByTeacherIdAndStartTimeBetween(leave.getTeacher().getId(),start,end);

        // Create response object
        List<LessonWAvailableTeachersResponse> responses = new ArrayList<>();

        // Find available teachers for each lesson
        for (Lesson lesson : conflictingLessons) {
            LocalDate lessonDate = lesson.getStartTime().toLocalDate();
            List<TeacherDto> availableTeachers =
                    teacherRepository.findAllAvailableTeachersByCourseAndNotOnLeave(lesson.getStartTime(), lesson.getEndTime(),lessonDate,lesson.getCourse().getId())
                            .stream()
                            .map(teacherMapper::toTeacherDto)
                            .toList();

            LessonWAvailableTeachersResponse res =
                    new LessonWAvailableTeachersResponse(
                        lesson.getId(),
                        lesson.getName(),
                        lesson.getDescription(),
                        lesson.getStartTime(),
                        lesson.getEndTime(),
                        lesson.getClassSize(),
                        lesson.getStatus(),
                        availableTeachers
                    );

            responses.add(res);
        }

        return responses;
    }

    @Override
    public List<LessonWTeacherResponse> getAllLessonsOfAllTeachersInvolved(Long leaveId) {
        // Get specific leave to resolve leave-lesson conflict
        TeacherLeave leave = teacherLeaveRepository.findById(leaveId)
                .orElseThrow(() -> new AppException("Leave with id " + leaveId + " not found"));

        LocalDateTime start = leave.getStartDate().atStartOfDay();
        LocalDateTime end = leave.getEndDate().atTime(23, 59, 59);

        // Get all lesson within leave period
        List<Lesson> conflictingLessons =
                lessonRepository.findByTeacherIdAndStartTimeBetween(leave.getTeacher().getId(),start,end);

        // Create response object
        Set<Long> availableTeacherIds = new HashSet<>();
        // add teacherId of teacher that is taking leave
        availableTeacherIds.add(leave.getTeacher().getId());

        // Find available teachers for each lesson
        for (Lesson lesson : conflictingLessons) {
            LocalDate lessonDate = lesson.getStartTime().toLocalDate();
            List<Teacher> availableTeachersForLesson =
                    teacherRepository.findAllAvailableTeachersByCourseAndNotOnLeave(lesson.getStartTime(), lesson.getEndTime(),lessonDate,lesson.getCourse().getId());

            for ( Teacher teacher : availableTeachersForLesson) {
                //add all available teacher to availableTeacherIds
                availableTeacherIds.add(teacher.getId());
            }
        }

        // get 3 months lesson from start leave
        LocalDateTime startMonth = leave.getStartDate().minusMonths(1).atStartOfDay();
        LocalDateTime endMonth = leave.getStartDate().plusMonths(2).atTime(23, 59, 59);


        return lessonRepository.findByTeacherIdInAndStartTimeBetween(availableTeacherIds,startMonth,endMonth).stream()
                .map(lessonMapper::toLessonWTeacherResponse)
                .toList();
    }
}
