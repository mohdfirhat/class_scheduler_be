package com.tfip.lessonscheduler.service;

import com.tfip.lessonscheduler.dto.TeacherLeaveWTeacherResponse;
import com.tfip.lessonscheduler.entity.Section;
import com.tfip.lessonscheduler.entity.Teacher;
import com.tfip.lessonscheduler.entity.TeacherLeave;
import com.tfip.lessonscheduler.exception.AppException;
import com.tfip.lessonscheduler.mapper.TeacherLeaveMapper;
import com.tfip.lessonscheduler.repository.SectionRepository;
import com.tfip.lessonscheduler.repository.TeacherLeaveRepository;
import com.tfip.lessonscheduler.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TeacherLeaveServiceImpl implements TeacherLeaveService {

    private TeacherRepository teacherRepository;
    private SectionRepository sectionRepository;
    private TeacherLeaveRepository leaveRepository;
    private TeacherLeaveMapper teacherLeaveMapper;

    public TeacherLeaveServiceImpl(TeacherRepository teacherRepository,
                                   SectionRepository sectionRepository,
                                   TeacherLeaveRepository leaveRepository,
                                   TeacherLeaveMapper teacherLeaveMapper) {
        this.teacherRepository = teacherRepository;
        this.sectionRepository = sectionRepository;
        this.leaveRepository = leaveRepository;
        this.teacherLeaveMapper = teacherLeaveMapper;
    }

    @Override
    public List<TeacherLeaveWTeacherResponse> getLeavesOfAllTeachersInvolved(Long leaveId) {
        // Get specific leave to resolve leave-schedule conflict
        TeacherLeave leave = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new AppException("Leave with id " + leaveId + " not found"));

        // Get all section within leave period
        List<Section> conflictingSections =
                sectionRepository.findByTeacherIdAndDateBetween(leave.getTeacher().getId(),leave.getStartDate(),leave.getEndDate());

        // Create response object
        Set<Long> availableTeacherIds = new HashSet<>();
        // add teacherId of teacher that is taking leave
        availableTeacherIds.add(leave.getTeacher().getId());

        // Find available teachers for each schedule
        for (Section schedule : conflictingSections) {
            List<Teacher> availableTeachersForSection =
                    teacherRepository.findAllAvailableTeachersByCourseAndNotOnLeave(schedule.getDate(), schedule.getTimeslot().getId(), schedule.getCourse().getId());

            for (Teacher teacher : availableTeachersForSection) {
                //add all available teacher to availableTeacherIds
                availableTeacherIds.add(teacher.getId());
            }
        }

        // get 3 months schedule from start leave
        LocalDate startMonth =
                leave.getStartDate().minusMonths(1).atStartOfDay().toLocalDate();
        LocalDate endMonth = leave.getStartDate().plusMonths(2).atTime(23, 59
                , 59).toLocalDate();

        return leaveRepository.findByTeacherIdInAndStartDateBetween(availableTeacherIds,startMonth,endMonth).stream()
                .map(teacherLeaveMapper::toTeacherLeaveWTeacherResponse)
                .toList();
    }

    @Override
    public TeacherLeaveWTeacherResponse getByIdWTeacher(Long leaveId) {
        TeacherLeave leave = leaveRepository.findById(leaveId).orElseThrow(() -> new AppException("Leave with id " + leaveId + " not found"));

        return teacherLeaveMapper.toTeacherLeaveWTeacherResponse(leave);
    }
}
