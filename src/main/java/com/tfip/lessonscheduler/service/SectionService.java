package com.tfip.lessonscheduler.service;

import com.tfip.lessonscheduler.dto.SectionWCourseAndVenueAndTeacherResponse;
import com.tfip.lessonscheduler.dto.SectionWCourseAndAvailableTeachersResponse;

import java.util.List;

public interface SectionService {
    List<SectionWCourseAndAvailableTeachersResponse> getSectionsWithAvailableTeachers(Long leaveId);

    List<SectionWCourseAndVenueAndTeacherResponse> getSectionsOfAllTeachersInvolved(Long leaveId);

}
