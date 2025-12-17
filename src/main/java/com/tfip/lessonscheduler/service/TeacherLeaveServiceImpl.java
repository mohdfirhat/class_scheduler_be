package com.tfip.lessonscheduler.service;

import com.tfip.lessonscheduler.dto.TeacherLeaveWConflictingSectionsResponse;
import com.tfip.lessonscheduler.entity.Section;
import com.tfip.lessonscheduler.entity.Teacher;
import com.tfip.lessonscheduler.entity.TeacherLeave;
import com.tfip.lessonscheduler.entity.TeacherLeaveStatus;
import com.tfip.lessonscheduler.exception.StatusConflictException;
import com.tfip.lessonscheduler.exception.ResourceNotFoundException;
import com.tfip.lessonscheduler.helpers.LeaveServiceHelpers;
import com.tfip.lessonscheduler.mapper.TeacherLeaveMapper;
import com.tfip.lessonscheduler.model.LeaveUpdatingDetails;
import com.tfip.lessonscheduler.repository.SectionRepository;
import com.tfip.lessonscheduler.repository.TeacherLeaveRepository;
import com.tfip.lessonscheduler.repository.TeacherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
public class TeacherLeaveServiceImpl implements TeacherLeaveService {

    private final TeacherRepository teacherRepository;
    private final SectionRepository sectionRepository;
    private final TeacherLeaveRepository leaveRepository;
    private final TeacherLeaveMapper teacherLeaveMapper;

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
    public List<TeacherLeave> getLeavesOfAllTeachersInvolved(Long leaveId) {
        // Get specific leave to resolve leave-schedule conflict
        TeacherLeave leave = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new ResourceNotFoundException("Leave with id " + leaveId + " not found"));

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

//        return leaveRepository.findByTeacherIdInAndStartDateBetween(availableTeacherIds,startMonth,endMonth).stream()
//                .map(teacherLeaveMapper::toTeacherLeaveWTeacherResponse)
//                .toList();
        return leaveRepository.findByTeacherIdInAndStartDateBetween(availableTeacherIds,startMonth,endMonth);
    }

    @Override
    public TeacherLeave getByIdWTeacher(Long leaveId) {
        TeacherLeave leave =
          leaveRepository.findById(leaveId).orElseThrow(() -> new ResourceNotFoundException(
            "Leave with id " + leaveId + " not found"));

//        return teacherLeaveMapper.toTeacherLeaveWTeacherResponse(leave);
        return leave;
    }

    @Override
    public List<TeacherLeave> getLeavesWithNonPendingStatus(){
        return leaveRepository.findLeavesWithNonPendingStatus();
    }

    @Override
    public List<TeacherLeave> getLeavesWithPendingStatus(){
        return leaveRepository.findLeavesWithPendingStatus();
    }

    @Override
    public List<TeacherLeave> getNonConflictingLeavesWithPendingStatus(){
        return leaveRepository.findNonConflictingLeavesWithPendingStatus();
    }

    @Override
    public List<TeacherLeaveWConflictingSectionsResponse> getConflictingLeavesWithAffectedSections(){
        List<TeacherLeaveWConflictingSectionsResponse>
                teacherLeavesWConflictingSections = new ArrayList<>();

        List<TeacherLeave> teacherLeaves =
                leaveRepository.findConflictingLeavesWithPendingStatus();

        if(!teacherLeaves.isEmpty()){
            for(TeacherLeave leave: teacherLeaves){
                List<Section> conflictingSections =
                        sectionRepository.findByTeacherIdAndDateBetween(
                                leave.getTeacher().getId(),
                                leave.getStartDate(),
                                leave.getEndDate());

                teacherLeavesWConflictingSections.add(
                        teacherLeaveMapper
                                .toTeacherLeaveWConflictingSectionsResponse(leave
                                        , conflictingSections));
            }
        }

        return teacherLeavesWConflictingSections;
    }

    @Override
    @Transactional
    public LeaveUpdatingDetails rejectLeave(Long leaveId){
        LeaveUpdatingDetails details;
        //check if the leave exists in the database
        TeacherLeave leave =
                leaveRepository.findById(leaveId).orElseThrow(() ->
                        new ResourceNotFoundException(
                        "Leave with id " + leaveId + " not found"));

        //check if leave has already been approved/rejected and return if it has
        details = LeaveServiceHelpers.checkLeaveStatusStillPending(leave);
        if (details.getMessage() != null){
            throw new StatusConflictException(details.getMessage());
        }

        //create a new 'rejected' status and save to leave entity, then call
        // repo to update the database
        TeacherLeaveStatus newStatus = new TeacherLeaveStatus(3L,
                "rejected");
        leave.setStatus(newStatus);
        leaveRepository.save(leave);

        details.setMessage("Leave " + leaveId +" has successfully been rejected.");
        return details;
    }

    @Override
    @Transactional
    public LeaveUpdatingDetails approveLeave(Long leaveId){
        LeaveUpdatingDetails details;
        //check if the leave exists in the database
        TeacherLeave leave =
                leaveRepository.findById(leaveId).orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Leave with id " + leaveId + " not found"));

        //check if leave has already been approved/rejected and return if it has
        details = LeaveServiceHelpers.checkLeaveStatusStillPending(leave);
        if (details.getMessage() != null){
            throw new StatusConflictException(details.getMessage());
        }

        //check if leave has new conflicts
        List<TeacherLeaveWConflictingSectionsResponse> leavesWithConflicts =
                getConflictingLeavesWithAffectedSections();

        details = LeaveServiceHelpers.checkLeaveForConflicts(leaveId,
                leavesWithConflicts);

        //return if leave has new conflicts
        if (details.getMessage() != null){
            throw new StatusConflictException(details.getMessage());
        }

        //create a new 'approved' status and save to leave entity, then call
        // repo to update the database
        TeacherLeaveStatus newStatus = new TeacherLeaveStatus(2L,
                "approved");
        leave.setStatus(newStatus);
        leaveRepository.save(leave);

        details.setMessage("Leave " + leaveId +" has successfully been " +
                "approved.");
        return details;

    }
}
