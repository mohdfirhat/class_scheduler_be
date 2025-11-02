package com.tfip.lessonscheduler.mapper;

import com.tfip.lessonscheduler.dto.SubjectDto;
import com.tfip.lessonscheduler.dto.TeacherWSubjectResponse;
import com.tfip.lessonscheduler.entity.Subject;
import com.tfip.lessonscheduler.entity.Teacher;
import org.springframework.stereotype.Component;

import java.util.HashSet;
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
}
