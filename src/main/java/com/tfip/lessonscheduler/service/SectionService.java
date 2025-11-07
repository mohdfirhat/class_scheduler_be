package com.tfip.lessonscheduler.service;

import com.tfip.lessonscheduler.dto.SectionWTeacherResponse;
import com.tfip.lessonscheduler.dto.SectionWAvailableTeachersResponse;

import java.util.List;

public interface SectionService {
    List<SectionWAvailableTeachersResponse> getAllSectionsWithAvailableTeachers(Long leaveId);

    List<SectionWTeacherResponse> getAllSectionsOfAllTeachersInvolved(Long leaveId);

}
