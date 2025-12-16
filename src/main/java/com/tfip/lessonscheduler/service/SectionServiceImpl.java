package com.tfip.lessonscheduler.service;

import com.tfip.lessonscheduler.dto.SectionCreateRequest;
import com.tfip.lessonscheduler.dto.SectionWCourseAndVenueAndTeacherResponse;
import com.tfip.lessonscheduler.dto.SectionWCourseAndAvailableTeachersResponse;
import com.tfip.lessonscheduler.dto.SubTeacherRequest;
import com.tfip.lessonscheduler.dto.TeacherDto;
import com.tfip.lessonscheduler.entity.*;
import com.tfip.lessonscheduler.exception.AppException;
import com.tfip.lessonscheduler.exception.BusinessLogicException;
import com.tfip.lessonscheduler.exception.ResourceNotFoundException;
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

        // Get all section confirmed within leave period
        List<Section> conflictingSections =
                sectionRepository.findConfirmedByTeacherIdAndDateBetween(leave.getTeacher().getId(),
                  leave.getStartDate(),leave.getEndDate());

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
    public void saveSection(SectionCreateRequest sectionCreateRequest) {
        // find teacher
        Teacher teacher = teacherRepository.findById(sectionCreateRequest.getTeacherId()).orElseThrow(()-> new IllegalArgumentException("Teacher with id" + sectionCreateRequest.getTeacherId() + "not found"));
        // find timeslot
        Timeslot timeslot = timeslotRepository.findById(sectionCreateRequest.getTimeslotId()).orElseThrow(()-> new  IllegalArgumentException("Timeslot with id" + sectionCreateRequest.getTimeslotId() + "not found"));
        // find venue
        Venue venue = venueRepository.findById(sectionCreateRequest.getVenueId()).orElseThrow(()-> new IllegalArgumentException("Venue with id" + sectionCreateRequest.getVenueId() + "not found"));
        // find course
        Course course = courseRepository.findById(sectionCreateRequest.getCourseId()).orElseThrow(()-> new IllegalArgumentException("Course with id" + sectionCreateRequest.getCourseId() + "not found"));
        // get status pending
        SectionStatus status = sectionStatusRepository.findById(1L).orElseThrow(()->new AppException("Server Error: Error with Section Status"));

        // venue available check
        List<Venue> availableVenues = venueRepository.findAllAvailableVenue(sectionCreateRequest.getClassSize(), sectionCreateRequest.getDate(), sectionCreateRequest.getTimeslotId());
        boolean isVenueAvailable = availableVenues.stream()
          .anyMatch(v -> v.getId().equals(venue.getId()));

        if (!isVenueAvailable) {
            throw new BusinessLogicException("Selected venue is not available for this date and timeslot.");
        }

        // Ensure teacher is teaching that subject
        teacherRepository.findTeacherByCourse(teacher.getId(),
          course.getId()).orElseThrow(()-> new IllegalArgumentException(
            "The teacher is not teaching the course."));

        // teacher available check
        List<Teacher> availableTeachers = teacherRepository.findAllAvailableTeachersByCourseAndNotOnLeave(sectionCreateRequest.getDate(), sectionCreateRequest.getTimeslotId(), sectionCreateRequest.getCourseId());
        boolean isTeacherAvailable = availableTeachers.stream()
          .anyMatch(t -> t.getId().equals(teacher.getId()));

        if (!isTeacherAvailable) {
            throw new BusinessLogicException("Selected teacher is not available for this date and timeslot.");
        }


        Section newSection = new Section(null,
          sectionCreateRequest.getRemark(),
          sectionCreateRequest.getDate(),
          sectionCreateRequest.getClassSize(),
          status,
          timeslot,
          teacher,
          venue,
          course
        );

        sectionRepository.save(newSection);
    }

    @Override
    @Transactional
    public void updateSectionTeacher(SubTeacherRequest subTeacherRequest) {
        // Get Section
        Section section = sectionRepository.findById(subTeacherRequest.getSectionId()).orElseThrow(()-> new IllegalArgumentException("Section with id" + subTeacherRequest.getSectionId() + "does not exist"));
        // Get Teacher
        Teacher newTeacher = teacherRepository.findById(subTeacherRequest.getTeacherId()).orElseThrow(()-> new IllegalArgumentException("Teacher with id" + subTeacherRequest.getTeacherId() + "does not exist"));

        // Ensure teacher is teaching that subject
        teacherRepository.findTeacherByCourse(newTeacher.getId(),
          section.getCourse().getId()).orElseThrow(()-> new BusinessLogicException("New Teacher does not teach that course"));


        // Ensure that newTeacher is available
        List<Teacher> availableTeachers = teacherRepository.findAllAvailableTeachersByCourseAndNotOnLeave(section.getDate(),section.getTimeslot().getId(),section.getCourse().getId());

        boolean isTeacherAvailable = availableTeachers.stream()
          .anyMatch(t -> t.getId().equals(newTeacher.getId()));

        // throw error if Teacher is not available
        if (!isTeacherAvailable) {
            throw new BusinessLogicException("Selected teacher is not available for this date and timeslot");
        }

        section.setTeacher(newTeacher);
        sectionRepository.save(section);
    }
}
