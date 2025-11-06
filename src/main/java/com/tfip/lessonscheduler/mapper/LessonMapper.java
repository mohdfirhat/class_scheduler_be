package com.tfip.lessonscheduler.mapper;

import com.tfip.lessonscheduler.dto.LessonDto;
import com.tfip.lessonscheduler.dto.LessonWTeacherResponse;
import com.tfip.lessonscheduler.dto.TeacherDto;
import com.tfip.lessonscheduler.entity.Lesson;
import com.tfip.lessonscheduler.entity.Teacher;
import org.springframework.stereotype.Component;

import java.sql.Time;

@Component
public class LessonMapper {

    public LessonDto toLessonDto(Lesson lesson) {
        return new LessonDto(
                lesson.getId(),
                lesson.getName(),
                lesson.getDescription(),
                lesson.getStartTime(),
                lesson.getEndTime(),
                lesson.getClassSize(),
                lesson.getStatus()
        );
    }

    public LessonWTeacherResponse toLessonWTeacherResponse(Lesson lesson) {
        if (lesson == null) {
            return null;
        }

        LessonWTeacherResponse response = new LessonWTeacherResponse();

        response.setId(lesson.getId());
        response.setName(lesson.getName());
        response.setDescription(lesson.getDescription());
        response.setStartTime(lesson.getStartTime());
        response.setEndTime(lesson.getEndTime());
        response.setClassSize(lesson.getClassSize());
        response.setStatus(lesson.getStatus());
        response.setTeacher(this.toTeacherDto(lesson.getTeacher()));

        return response;
    }

    public TeacherDto toTeacherDto(Teacher teacher) {
        if (teacher == null) {
            return null;
        }
        TeacherDto dto = new TeacherDto();
        dto.setId(teacher.getId());
        dto.setFirstName(teacher.getFirstName());
        dto.setLastName(teacher.getLastName());
        dto.setEmail(teacher.getEmail());
        dto.setLeaveDays(teacher.getLeaveDays());

        return dto;
    }
}
