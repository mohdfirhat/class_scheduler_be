package com.tfip.lessonscheduler.mapper;

import com.tfip.lessonscheduler.dto.TeacherDto;
import com.tfip.lessonscheduler.dto.TeacherLeaveDto;
import com.tfip.lessonscheduler.dto.TeacherLeaveWConflictingSectionsResponse;
import com.tfip.lessonscheduler.dto.TeacherLeaveWTeacherResponse;
import com.tfip.lessonscheduler.entity.Section;
import com.tfip.lessonscheduler.entity.Teacher;
import com.tfip.lessonscheduler.entity.TeacherLeave;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TeacherLeaveMapper {

    public TeacherLeaveDto toTeacherLeave(TeacherLeave leave) {
        return new TeacherLeaveDto(
                leave.getId(),
                leave.getStartDate(),
                leave.getEndDate(),
                leave.getStatus()
        );
    }

    public TeacherLeaveWTeacherResponse toTeacherLeaveWTeacherResponse(TeacherLeave leave) {
        if (leave == null) {
            return null;
        }

        TeacherLeaveWTeacherResponse response = new TeacherLeaveWTeacherResponse();

        response.setId(leave.getId());
        response.setStartDate(leave.getStartDate());
        response.setEndDate(leave.getEndDate());
        response.setStatus(leave.getStatus());
        response.setTeacher(this.toTeacherDto(leave.getTeacher()));

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

    public TeacherLeaveWConflictingSectionsResponse toTeacherLeaveWConflictingSectionsResponse(
            TeacherLeave teacherLeave, List<Section> sectionList){
        TeacherLeaveWConflictingSectionsResponse dto =
                new TeacherLeaveWConflictingSectionsResponse();
        dto.setId(teacherLeave.getId());
        dto.setStartDate(teacherLeave.getStartDate());
        dto.setEndDate(teacherLeave.getEndDate());
        dto.setStatus(teacherLeave.getStatus());
        dto.setTeacher(teacherLeave.getTeacher());
        dto.setConflictingSections(sectionList);

        return dto;
    }

}
