package com.tfip.lessonscheduler.service;

import com.tfip.lessonscheduler.dto.SectionWCourseAndVenueAndTeacherResponse;
import com.tfip.lessonscheduler.dto.SectionWCourseAndAvailableTeachersResponse;
import com.tfip.lessonscheduler.entity.Section;

import java.util.List;

public interface SectionService {
    List<SectionWCourseAndAvailableTeachersResponse> getSectionsWithAvailableTeachers(Long leaveId);

    List<SectionWCourseAndVenueAndTeacherResponse> getSectionsOfAllTeachersInvolved(Long leaveId);

    List<Section> getAllSections();
}
