package com.tfip.lessonscheduler.mapper;

import com.tfip.lessonscheduler.dto.SectionDto;
import com.tfip.lessonscheduler.dto.SectionWTeacherResponse;
import com.tfip.lessonscheduler.dto.TeacherDto;
import com.tfip.lessonscheduler.entity.Section;
import com.tfip.lessonscheduler.entity.Teacher;
import org.springframework.stereotype.Component;

@Component
public class SectionMapper {

    public SectionDto toSectionDto(Section section) {
        return new SectionDto(
                section.getId(),
                section.getName(),
                section.getDescription(),
                section.getDate(),
                section.getTimeslot(),
                section.getClassSize(),
                section.getStatus()
        );
    }

    public SectionWTeacherResponse toSectionWTeacherResponse(Section section) {
        if (section == null) {
            return null;
        }

        SectionWTeacherResponse response = new SectionWTeacherResponse();

        response.setId(section.getId());
        response.setName(section.getName());
        response.setDescription(section.getDescription());
        response.setDate(section.getDate());
        response.setTimeslot(section.getTimeslot());
        response.setClassSize(section.getClassSize());
        response.setStatus(section.getStatus());
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
}
