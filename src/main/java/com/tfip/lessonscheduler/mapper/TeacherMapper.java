package com.tfip.lessonscheduler.mapper;

import com.tfip.lessonscheduler.dto.*;
import com.tfip.lessonscheduler.entity.Section;
import com.tfip.lessonscheduler.entity.Course;
import com.tfip.lessonscheduler.entity.Teacher;
import com.tfip.lessonscheduler.entity.TeacherLeave;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class TeacherMapper {

    public TeacherWCoursesAndDepartmentResponse toTeacherWCourseAndDepartmentResponse(Teacher teacher) {
        if (teacher == null) {
            return null;
        }

        TeacherWCoursesAndDepartmentResponse response = new TeacherWCoursesAndDepartmentResponse();

        response.setId(teacher.getId());
        response.setFirstName(teacher.getFirstName());
        response.setLastName(teacher.getLastName());
        response.setEmail(teacher.getEmail());
        response.setLeaveDays(teacher.getLeaveDays());
        response.setDepartment(teacher.getDepartment());
        response.setAvatar(teacher.getAvatar());

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

    public TeacherWithLeavesAndSectionsResponse toTeacherWLeavesAndSectionsResponse(Teacher teacher) {
        if (teacher == null) {
            return null;
        }

        TeacherWithLeavesAndSectionsResponse response = new TeacherWithLeavesAndSectionsResponse();

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
            response.setLeaves(leaveDtos);
        } else {
            response.setLeaves(new TeacherLeaveDto[0]);
        }

        // Convert Set<Section> → CourseDto[]
        Set<Section> lesson = teacher.getSections();
        if (lesson != null && !lesson.isEmpty()) {
            SectionWCourseAndVenueResponse[] lessonDtos = lesson.stream()
                    .map(this::toSectionWCourseAndVenueResponse)
                    .toArray(SectionWCourseAndVenueResponse[]::new);
            response.setSections(lessonDtos);
        } else {
            response.setSections(new SectionWCourseAndVenueResponse[0]);
        }

        return response;
    }

    // ---------- Helper Methods ----------

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
    private SectionDto toSectionDto(Section lesson) {
        if (lesson == null) {
            return null;
        }
        SectionDto dto = new SectionDto();
        dto.setId(lesson.getId());
        dto.setName(lesson.getName());
        dto.setDescription(lesson.getDescription());
        dto.setDate(lesson.getDate());
        dto.setTimeslot(lesson.getTimeslot());
        dto.setClassSize(lesson.getClassSize());
        dto.setStatus(lesson.getStatus());

        return dto;
    }

    public SectionWCourseAndVenueResponse toSectionWCourseAndVenueResponse(Section section) {
        if (section == null) {
            return null;
        }

        SectionWCourseAndVenueResponse response =
          new SectionWCourseAndVenueResponse();

        response.setId(section.getId());
        response.setName(section.getName());
        response.setDescription(section.getDescription());
        response.setDate(section.getDate());
        response.setTimeslot(section.getTimeslot());
        response.setClassSize(section.getClassSize());
        response.setStatus(section.getStatus());
        response.setCourse(this.toCourseDto(section.getCourse()));
        response.setVenue(section.getVenue());

        return response;
    }
}
