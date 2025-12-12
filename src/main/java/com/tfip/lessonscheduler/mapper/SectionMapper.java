package com.tfip.lessonscheduler.mapper;

import com.tfip.lessonscheduler.dto.CourseDto;
import com.tfip.lessonscheduler.dto.SectionDto;
import com.tfip.lessonscheduler.dto.SectionWCourseAndVenueAndTeacherResponse;
import com.tfip.lessonscheduler.dto.TeacherDto;
import com.tfip.lessonscheduler.entity.Course;
import com.tfip.lessonscheduler.entity.Section;
import com.tfip.lessonscheduler.entity.Teacher;
import org.springframework.stereotype.Component;

@Component
public class SectionMapper {

    public SectionDto toSectionDto(Section section) {
        return new SectionDto(
                section.getId(),
                section.getRemark(),
                section.getDate(),
                section.getTimeslot(),
                section.getClassSize(),
                section.getStatus()
        );
    }

    public SectionWCourseAndVenueAndTeacherResponse toSectionWCourseAndVenueAndTeacherResponse(Section section) {
        if (section == null) {
            return null;
        }

        SectionWCourseAndVenueAndTeacherResponse response = new SectionWCourseAndVenueAndTeacherResponse();

        response.setId(section.getId());
        response.setRemark(section.getRemark());
        response.setDate(section.getDate());
        response.setTimeslot(section.getTimeslot());
        response.setClassSize(section.getClassSize());
        response.setStatus(section.getStatus());
        response.setCourse(this.toCourseDto(section.getCourse()));
        response.setVenue(section.getVenue());
        response.setTeacher(this.toTeacherDto(section.getTeacher()));

        return response;
    }

    // ---------- Helper Methods ----------

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
    public CourseDto toCourseDto(Course course) {
        return new CourseDto(
                course.getId(),
                course.getName(),
                course.getCourseCode()
        );
    }
}
