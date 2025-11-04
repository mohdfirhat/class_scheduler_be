package com.tfip.lessonscheduler.mapper;

import com.tfip.lessonscheduler.dto.*;
import com.tfip.lessonscheduler.entity.Lesson;
import com.tfip.lessonscheduler.entity.Subject;
import com.tfip.lessonscheduler.entity.Teacher;
import com.tfip.lessonscheduler.entity.TeacherLeave;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class TeacherMapper {
    public TeacherWSubjectsResponse toTeacherWSubjectResponse(Teacher teacher) {
        if (teacher == null) {
            return null;
        }

        TeacherWSubjectsResponse response = new TeacherWSubjectsResponse();

        response.setId(teacher.getId());
        response.setFirstName(teacher.getFirstName());
        response.setLastName(teacher.getLastName());
        response.setEmail(teacher.getEmail());
        response.setLeaveDays(teacher.getLeaveDays());

        // Convert Set<Subject> → SubjectDto[]
        Set<Subject> subjects = teacher.getSubjects();
        if (subjects != null && !subjects.isEmpty()) {
            SubjectDto[] subjectDtos = subjects.stream()
                    .map(this::toSubjectDto)
                    .toArray(SubjectDto[]::new);
            response.setSubjects(subjectDtos);
        } else {
            response.setSubjects(new SubjectDto[0]);
        }

        return response;
    }

    public TeacherWLeavesResponse toTeacherWLeaveResponse(Teacher teacher) {
        if (teacher == null) {
            return null;
        }

        TeacherWLeavesResponse response = new TeacherWLeavesResponse();

        response.setId(teacher.getId());
        response.setFirstName(teacher.getFirstName());
        response.setLastName(teacher.getLastName());
        response.setEmail(teacher.getEmail());
        response.setLeaveDays(teacher.getLeaveDays());

        // Convert Set<TeacherLeave> → SubjectDto[]
        Set<TeacherLeave> leaves = teacher.getTeacherLeaves();
        if (leaves != null && !leaves.isEmpty()) {
            TeacherLeaveDto[] leaveDtos = leaves.stream()
                    .map(this::toTeacherLeaveDto)
                    .toArray(TeacherLeaveDto[]::new);
            response.setTeacherLeaves(leaveDtos);
        } else {
            response.setTeacherLeaves(new TeacherLeaveDto[0]);
        }

        return response;
    }

    public TeacherWithLeavesAndLessonsResponse toTeacherWLeavesAndLessonsResponse(Teacher teacher) {
        if (teacher == null) {
            return null;
        }

        TeacherWithLeavesAndLessonsResponse response = new TeacherWithLeavesAndLessonsResponse();

        response.setId(teacher.getId());
        response.setFirstName(teacher.getFirstName());
        response.setLastName(teacher.getLastName());
        response.setEmail(teacher.getEmail());
        response.setLeaveDays(teacher.getLeaveDays());

        // Convert Set<TeacherLeave> → SubjectDto[]
        Set<TeacherLeave> leaves = teacher.getTeacherLeaves();
        if (leaves != null && !leaves.isEmpty()) {
            TeacherLeaveDto[] leaveDtos = leaves.stream()
                    .map(this::toTeacherLeaveDto)
                    .toArray(TeacherLeaveDto[]::new);
            response.setTeacherLeaves(leaveDtos);
        } else {
            response.setTeacherLeaves(new TeacherLeaveDto[0]);
        }

        // Convert Set<Lesson> → SubjectDto[]
        Set<Lesson> lesson = teacher.getLessons();
        if (lesson != null && !lesson.isEmpty()) {
            LessonDto[] lessonDtos = lesson.stream()
                    .map(this::toLessonDto)
                    .toArray(LessonDto[]::new);
            response.setLessons(lessonDtos);
        } else {
            response.setLessons(new LessonDto[0]);
        }

        return response;
    }

    private SubjectDto toSubjectDto(Subject subject) {
        if (subject == null) {
            return null;
        }
        SubjectDto dto = new SubjectDto();
        dto.setId(subject.getId());
        dto.setName(subject.getName()); // adjust field names as per your Subject entity
        dto.setSubjectCode(subject.getSubjectCode());
        return dto;
    }

    private TeacherLeaveDto toTeacherLeaveDto(TeacherLeave leave) {
        if (leave == null) {
            return null;
        }
        TeacherLeaveDto dto = new TeacherLeaveDto();
        dto.setId(leave.getId());
        dto.setStartDate(leave.getStartDate());
        dto.setEndDate(leave.getEndDate());
        dto.setStatus(leave.getStatus());

        return dto;
    }
    private LessonDto toLessonDto(Lesson lesson) {
        if (lesson == null) {
            return null;
        }
        LessonDto dto = new LessonDto();
        dto.setId(lesson.getId());
        dto.setName(lesson.getName());
        dto.setDescription(lesson.getDescription());
        dto.setStartTime(lesson.getStartTime());
        dto.setEndTime(lesson.getEndTime());
        dto.setClassSize(lesson.getClassSize());
        dto.setStatus(lesson.getStatus());

        return dto;
    }
}
