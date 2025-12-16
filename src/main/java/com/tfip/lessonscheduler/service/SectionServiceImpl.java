package com.tfip.lessonscheduler.service;

import com.tfip.lessonscheduler.dto.SectionCreateDto;
import com.tfip.lessonscheduler.dto.SectionWCourseAndVenueAndTeacherResponse;
import com.tfip.lessonscheduler.dto.SectionWCourseAndAvailableTeachersResponse;
import com.tfip.lessonscheduler.dto.TeacherDto;
import com.tfip.lessonscheduler.entity.*;
import com.tfip.lessonscheduler.exception.AppException;
import com.tfip.lessonscheduler.exception.ResourceNotFoundException;
import com.tfip.lessonscheduler.helpers.SectionServiceHelpers;
import com.tfip.lessonscheduler.mapper.CourseMapper;
import com.tfip.lessonscheduler.mapper.SectionMapper;
import com.tfip.lessonscheduler.mapper.TeacherMapper;
import com.tfip.lessonscheduler.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SectionServiceImpl implements SectionService {

    private final SectionRepository sectionRepository;
    private final TeacherLeaveRepository teacherLeaveRepository;
    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;
    private final SectionMapper sectionMapper;
    private final CourseMapper courseMapper;
    private final TimeslotRepository timeslotRepository;
    private final VenueRepository venueRepository;
    private final CourseRepository courseRepository;
    private final SectionStatusRepository sectionStatusRepository;

    public SectionServiceImpl(SectionRepository sectionRepository, TeacherLeaveRepository teacherLeaveRepository, TeacherRepository teacherRepository, TeacherMapper teacherMapper, SectionMapper sectionMapper, CourseMapper courseMapper, TimeslotRepository timeslotRepository, VenueRepository venueRepository, CourseRepository courseRepository,SectionStatusRepository sectionStatusRepository) {
        this.sectionRepository = sectionRepository;
        this.teacherLeaveRepository = teacherLeaveRepository;
        this.teacherRepository = teacherRepository;
        this.teacherMapper = teacherMapper;
        this.sectionMapper = sectionMapper;
        this.courseMapper = courseMapper;
        this.timeslotRepository = timeslotRepository;
        this.venueRepository = venueRepository;
        this.courseRepository = courseRepository;
        this.sectionStatusRepository = sectionStatusRepository;
    }


    @Override
    public List<SectionWCourseAndAvailableTeachersResponse> getSectionsWithAvailableTeachers(Long leaveId) {

        // Get specific leave to resolve leave-section conflict
        TeacherLeave leave = teacherLeaveRepository.findById(leaveId)
          .orElseThrow(() -> new ResourceNotFoundException("Leave with " +
            "id " + leaveId + " not found"));

//        LocalDateTime start = leave.getStartDate().atStartOfDay();
//        LocalDateTime end = leave.getEndDate().atTime(23, 59, 59);

        // Get all section within leave period
        List<Section> conflictingSections =
                sectionRepository.findByTeacherIdAndDateBetween(leave.getTeacher().getId(),leave.getStartDate(),leave.getEndDate());

        // Create response object
        List<SectionWCourseAndAvailableTeachersResponse> responses = new ArrayList<>();

        // Find available teachers for each section
        for (Section section : conflictingSections) {
            List<TeacherDto> availableTeachers =
                    teacherRepository.findAllAvailableTeachersByCourseAndNotOnLeave(section.getDate(),section.getTimeslot().getId(),section.getCourse().getId())
                            .stream()
                            .map(teacherMapper::toTeacherDto)
                            .toList();

            SectionWCourseAndAvailableTeachersResponse res =
                    new SectionWCourseAndAvailableTeachersResponse(
                        section.getId(),
                        section.getRemark(),
                        section.getDate(),
                        section.getTimeslot(),
                        section.getClassSize(),
                        section.getStatus(),
                        courseMapper.toCourseDto(section.getCourse()),
                        availableTeachers
                    );

            responses.add(res);
        }

        return responses;
    }

    @Override
    public List<SectionWCourseAndVenueAndTeacherResponse> getSectionsOfAllTeachersInvolved(Long leaveId) {
        // Get specific leave to resolve leave-section conflict
        TeacherLeave leave = teacherLeaveRepository.findById(leaveId)
                .orElseThrow(() -> new ResourceNotFoundException("Leave with id " + leaveId +
                  " not found"));

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
                .map(sectionMapper::toSectionWCourseAndVenueAndTeacherResponse)
                .toList();
    }

    @Override
    public List<Section> getAllSections(){
        return sectionRepository.findAll();
    }

    @Override
    public SectionWCourseAndVenueAndTeacherResponse getSectionById(Long sectionId) {
        Section section = sectionRepository.findById(sectionId).orElseThrow(()-> new ResourceNotFoundException("Section with id" + sectionId + "not found"));

        return sectionMapper.toSectionWCourseAndVenueAndTeacherResponse(section);
    }

    @Override
    @Transactional
    public void saveSection(SectionCreateDto sectionCreateDto) {
        // find teacher
        Teacher teacher = teacherRepository.findById(sectionCreateDto.getTeacherId()).orElseThrow(()-> new IllegalArgumentException("Teacher with id" + sectionCreateDto.getTeacherId() + "not found"));
        // find timeslot
        Timeslot timeslot = timeslotRepository.findById(sectionCreateDto.getTimeslotId()).orElseThrow(()-> new  IllegalArgumentException("Timeslot with id" + sectionCreateDto.getTimeslotId() + "not found"));
        // find venue
        Venue venue = venueRepository.findById(sectionCreateDto.getVenueId()).orElseThrow(()-> new IllegalArgumentException("Venue with id" + sectionCreateDto.getVenueId() + "not found"));
        // find course
        Course course = courseRepository.findById(sectionCreateDto.getCourseId()).orElseThrow(()-> new IllegalArgumentException("Course with id" + sectionCreateDto.getCourseId() + "not found"));
        // get status pending
        SectionStatus status = sectionStatusRepository.findById(1L).orElseThrow(()->new AppException("Server Error: Error with Section Status"));

        // venue available check
        List<Venue> availableVenues = venueRepository.findAllAvailableVenue(sectionCreateDto.getClassSize(),sectionCreateDto.getDate(), sectionCreateDto.getTimeslotId());
        boolean isVenueAvailable = availableVenues.stream()
          .anyMatch(v -> v.getId().equals(venue.getId()));

        if (!isVenueAvailable) {
            throw new IllegalArgumentException("Selected venue is not available for this date and timeslot.");
        }
        // teacher available check
        List<Teacher> availableTeachers = teacherRepository.findAllAvailableTeachersByCourseAndNotOnLeave(sectionCreateDto.getDate(), sectionCreateDto.getTimeslotId(), sectionCreateDto.getCourseId());
        boolean isTeacherAvailable = availableTeachers.stream()
          .anyMatch(t -> t.getId().equals(teacher.getId()));

        if (!isTeacherAvailable) {
            throw new IllegalArgumentException("Selected teacher is not available for this date and timeslot or do not teacher this course.");
        }


        Section newSection = new Section(null,
          sectionCreateDto.getRemark(),
          sectionCreateDto.getDate(),
          sectionCreateDto.getClassSize(),
          status,
          timeslot,
          teacher,
          venue,
          course
        );

        sectionRepository.save(newSection);
    }
    //todo implement cancel and approve section logic
    @Transactional
    public String cancelSection(Long sectionId){

        //check if the section exists in the database
        Section section = sectionRepository.findById(sectionId).orElseThrow(() ->
                new ResourceNotFoundException(
                        "Leave with id " + sectionId + " not found."));

        //check if the section has already been approved/canceled and return if
        // it has
        SectionServiceHelpers.checkIfSectionAlreadyRejected(section);

        //create new SectionStatus and assign to section, then call repo to
        // update the database
        SectionStatus newStatus = new SectionStatus(3L, "rejected");
        section.setStatus(newStatus);
        sectionRepository.save(section);
        return "Section " + sectionId + " has successfully been cancelled.";
    }

    @Transactional
    public String approveSection(Long sectionId){

        //check if the section exists in the database
        Section section = sectionRepository.findById(sectionId).orElseThrow(() ->
                new ResourceNotFoundException(
                        "Leave with id " + sectionId + " not found."));

        //check if the section has already been approved/canceled and return if
        // it has
        SectionServiceHelpers.checkIfSectionStatusStillPending(section);

        //create new SectionStatus and assign to section, then call repo to
        // update the database
        SectionStatus newStatus = new SectionStatus(2L, "approved");
        section.setStatus(newStatus);
        sectionRepository.save(section);
        return "Section " + sectionId + " has successfully been approved.";
    }

}
