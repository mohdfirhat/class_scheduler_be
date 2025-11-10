package com.tfip.lessonscheduler.service;

import com.tfip.lessonscheduler.dto.SectionWTeacherResponse;
import com.tfip.lessonscheduler.dto.SectionWAvailableTeachersResponse;
import com.tfip.lessonscheduler.dto.TeacherDto;
import com.tfip.lessonscheduler.entity.Section;
import com.tfip.lessonscheduler.entity.Teacher;
import com.tfip.lessonscheduler.entity.TeacherLeave;
import com.tfip.lessonscheduler.exception.AppException;
import com.tfip.lessonscheduler.mapper.SectionMapper;
import com.tfip.lessonscheduler.mapper.TeacherMapper;
import com.tfip.lessonscheduler.repository.SectionRepository;
import com.tfip.lessonscheduler.repository.TeacherLeaveRepository;
import com.tfip.lessonscheduler.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SectionServiceImpl implements SectionService {

    private SectionRepository sectionRepository;
    private TeacherLeaveRepository teacherLeaveRepository;
    private TeacherRepository teacherRepository;
    private TeacherMapper teacherMapper;
    private SectionMapper sectionMapper;

    public SectionServiceImpl(SectionRepository sectionRepository,TeacherLeaveRepository teacherLeaveRepository,TeacherRepository teacherRepository, TeacherMapper teacherMapper, SectionMapper sectionMapper) {
        this.sectionRepository = sectionRepository;
        this.teacherLeaveRepository = teacherLeaveRepository;
        this.teacherRepository = teacherRepository;
        this.teacherMapper = teacherMapper;
        this.sectionMapper = sectionMapper;
    }


    @Override
    public List<SectionWAvailableTeachersResponse> getSectionsWithAvailableTeachers(Long leaveId) {

        // Get specific leave to resolve leave-section conflict
        TeacherLeave leave = teacherLeaveRepository.findById(leaveId)
                .orElseThrow(() -> new AppException("Leave with id " + leaveId + " not found"));

//        LocalDateTime start = leave.getStartDate().atStartOfDay();
//        LocalDateTime end = leave.getEndDate().atTime(23, 59, 59);

        // Get all section within leave period
        List<Section> conflictingSections =
                sectionRepository.findByTeacherIdAndDateBetween(leave.getTeacher().getId(),leave.getStartDate(),leave.getEndDate());

        // Create response object
        List<SectionWAvailableTeachersResponse> responses = new ArrayList<>();

        // Find available teachers for each section
        for (Section section : conflictingSections) {
            List<TeacherDto> availableTeachers =
                    teacherRepository.findAllAvailableTeachersByCourseAndNotOnLeave(section.getDate(),section.getTimeslot().getId(),section.getCourse().getId())
                            .stream()
                            .map(teacherMapper::toTeacherDto)
                            .toList();

            SectionWAvailableTeachersResponse res =
                    new SectionWAvailableTeachersResponse(
                        section.getId(),
                        section.getName(),
                        section.getDescription(),
                        section.getDate(),
                        section.getTimeslot(),
                        section.getClassSize(),
                        section.getStatus(),
                        availableTeachers
                    );

            responses.add(res);
        }

        return responses;
    }

    @Override
    public List<SectionWTeacherResponse> getSectionsOfAllTeachersInvolved(Long leaveId) {
        // Get specific leave to resolve leave-section conflict
        TeacherLeave leave = teacherLeaveRepository.findById(leaveId)
                .orElseThrow(() -> new AppException("Leave with id " + leaveId + " not found"));

        // Get all section within leave period
        List<Section> conflictingSections =
                sectionRepository.findByTeacherIdAndDateBetween(leave.getTeacher().getId(),leave.getStartDate(),leave.getEndDate());

        // Create response object
        Set<Long> availableTeacherIds = new HashSet<>();
        // add teacherId of teacher that is taking leave
        availableTeacherIds.add(leave.getTeacher().getId());

        // Find available teachers for each section
        for (Section section : conflictingSections) {
            List<Teacher> availableTeachersForSection =
                    teacherRepository.findAllAvailableTeachersByCourseAndNotOnLeave(section.getDate(), section.getTimeslot().getId(),section.getCourse().getId());

            for ( Teacher teacher : availableTeachersForSection) {
                //add all available teacher to availableTeacherIds
                availableTeacherIds.add(teacher.getId());
            }
        }

        // get 3 months section from start leave
        LocalDate startMonth = leave.getStartDate().minusMonths(1);
        LocalDate endMonth = leave.getStartDate().plusMonths(2);


        return sectionRepository.findByTeacherIdInAndDateBetween(availableTeacherIds,startMonth,endMonth).stream()
                .map(sectionMapper::toSectionWTeacherResponse)
                .toList();
    }
}
