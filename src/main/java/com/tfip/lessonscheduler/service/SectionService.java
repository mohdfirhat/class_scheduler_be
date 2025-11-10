package com.tfip.lessonscheduler.service;

import com.tfip.lessonscheduler.dto.SectionWTeacherResponse;
import com.tfip.lessonscheduler.dto.SectionWAvailableTeachersResponse;

import java.util.List;

public interface SectionService {
    List<SectionWAvailableTeachersResponse> getSectionsWithAvailableTeachers(Long leaveId);

    List<SectionWTeacherResponse> getSectionsOfAllTeachersInvolved(Long leaveId);

}
