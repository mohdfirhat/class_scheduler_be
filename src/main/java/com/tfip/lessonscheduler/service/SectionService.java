package com.tfip.lessonscheduler.service;

import com.tfip.lessonscheduler.dto.SectionCreateRequest;
import com.tfip.lessonscheduler.dto.SectionWCourseAndAvailableTeachersResponse;
import com.tfip.lessonscheduler.dto.SubTeacherRequest;
import com.tfip.lessonscheduler.entity.Section;

import java.util.List;

public interface SectionService {
    List<SectionWCourseAndAvailableTeachersResponse> getSectionsWithAvailableTeachers(Long leaveId);

    List<Section> getSectionsOfAllTeachersInvolved(Long leaveId);

    List<Section> getAllSections();

    Section getSectionById(Long sectionId);

    String cancelSection(Long sectionId);

    String approveSection(Long sectionId);

    void saveSection(SectionCreateRequest sectionCreateRequest);

    void updateSectionTeacher(SubTeacherRequest subTeacherRequest);
}
