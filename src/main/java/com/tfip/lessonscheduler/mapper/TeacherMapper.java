package com.tfip.lessonscheduler.mapper;

import com.tfip.lessonscheduler.dto.*;
import com.tfip.lessonscheduler.entity.Lesson;
import com.tfip.lessonscheduler.entity.Course;
import com.tfip.lessonscheduler.entity.Teacher;
import com.tfip.lessonscheduler.entity.TeacherLeave;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class TeacherMapper {
    public TeacherWCoursesResponse toTeacherWCourseResponse(Teacher teacher) {
        if (teacher == null) {
            return null;
        }

        TeacherWCoursesResponse response = new TeacherWCoursesResponse();

        response.setId(teacher.getId());
        response.setFirstName(teacher.getFirstName());
        response.setLastName(teacher.getLastName());
        response.setEmail(teacher.getEmail());
        response.setLeaveDays(teacher.getLeaveDays());

        // Convert Set<Course> → CourseDto[]
        Set<Course> courses = teacher.getCourses();
        if (courses != null && !courses.isEmpty()) {
            CourseDto[] courseDtos = courses.stream()
                    .map(this::toCourseDto)
                    .toArray(CourseDto[]::new);
            response.setCourses(courseDtos);
        } else {
            response.setCourses(new CourseDto[0]);
        }

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

        // Convert Set<TeacherLeave> → CourseDto[]
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

        // Convert Set<TeacherLeave> → CourseDto[]
        Set<TeacherLeave> leaves = teacher.getTeacherLeaves();
        if (leaves != null && !leaves.isEmpty()) {
            TeacherLeaveDto[] leaveDtos = leaves.stream()
                    .map(this::toTeacherLeaveDto)
                    .toArray(TeacherLeaveDto[]::new);
            response.setTeacherLeaves(leaveDtos);
        } else {
            response.setTeacherLeaves(new TeacherLeaveDto[0]);
        }

        // Convert Set<Lesson> → CourseDto[]
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

    private CourseDto toCourseDto(Course course) {
        if (course == null) {
            return null;
        }
        CourseDto dto = new CourseDto();
        dto.setId(course.getId());
        dto.setName(course.getName()); // adjust field names as per your Course entity
        dto.setCourseCode(course.getCourseCode());
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
