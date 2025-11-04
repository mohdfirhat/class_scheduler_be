package com.tfip.lessonscheduler.mapper;

import com.tfip.lessonscheduler.dto.SubjectDto;
import com.tfip.lessonscheduler.dto.TeacherLeaveDto;
import com.tfip.lessonscheduler.dto.TeacherWLeaveResponse;
import com.tfip.lessonscheduler.dto.TeacherWSubjectResponse;
import com.tfip.lessonscheduler.entity.Subject;
import com.tfip.lessonscheduler.entity.Teacher;
import com.tfip.lessonscheduler.entity.TeacherLeave;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class TeacherMapper {
    public TeacherWSubjectResponse toTeacherWSubjectResponse(Teacher teacher) {
        if (teacher == null) {
            return null;
        }

        TeacherWSubjectResponse response = new TeacherWSubjectResponse();

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

    public TeacherWLeaveResponse toTeacherWLeaveResponse(Teacher teacher) {
        if (teacher == null) {
            return null;
        }

        TeacherWLeaveResponse response = new TeacherWLeaveResponse();

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
}
