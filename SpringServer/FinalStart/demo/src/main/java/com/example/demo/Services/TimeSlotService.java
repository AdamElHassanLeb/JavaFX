package com.example.demo.Services;

import com.example.demo.Exceptions.ResourceNotFoundException;
import com.example.demo.Exceptions.ServiceConstraintViolationException;
import com.example.demo.Models.*;
import com.example.demo.Repositories.*;
import com.example.demo.Utils.*;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TimeSlotService {

    private final UserService userService;
    private final TimeGroupService timeGroupService;
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    public TimeSlotService(TimeSlotRepository timeSlotRepository, UserService userService, TimeGroupService timeGroupService) { this.timeSlotRepository = timeSlotRepository;
        this.userService = userService;
        this.timeGroupService = timeGroupService;
    }

    public List<TimeSlot> getAllTimeSlots() { return timeSlotRepository.findAll(); }

    public TimeSlot getTimeSlotById(long id) {
        return timeSlotRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Timeslot not found"));
    }

    public List<TimeSlot> getTimeSlotsByUser(User user) {
        return timeSlotRepository.getTimeSlotsByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Timeslot not found"));
    }

    public List<TimeSlot> getTimeSlotsByGroup(TimeGroup timeGroup) {
        return timeSlotRepository.getTimeSlotsByGroup(timeGroup)
                .orElseThrow(() -> new ResourceNotFoundException("Timeslot not found"));
    }

    public List<TimeSlot> getTimeSlotsByGroupAndMonth(TimeGroup timeGroup, int month) {
        return timeSlotRepository.getTimeSlotsByGroupAndMonth(timeGroup, month)
                .orElseThrow(() -> new ResourceNotFoundException("Timeslot not found"));
    }

    public List<TimeSlot> getTimeSlotsByGroupAndDay(TimeGroup timeGroup, int day, int month, int year) {
        return timeSlotRepository.getTimeSlotsByGroupAndDay(timeGroup, day, month, year)
                .orElseThrow(() -> new ResourceNotFoundException("Timeslot not found"));
    }

    public TimeSlot insertTimeSlot(TimeSlot timeSlot) {
        try {
            if(TimeslotOverlap.isTimeSlotOverlap(timeSlot))
                throw new ServiceConstraintViolationException("TimeSlot overlap");

            if(timeSlot.getUser() != null)
                timeSlot.setUser(userService.getUserById(timeSlot.getUser().getId()));

            timeSlot.setGroup(timeGroupService.getTimeGroupById(timeSlot.getGroup().getId()));
            return timeSlotRepository.save(timeSlot);
        }catch (DataIntegrityViolationException e){
            String errorMessage = e.getRootCause().getMessage(); // Extract the error message
            throw new ServiceConstraintViolationException(errorMessage); // Wrap the error message in a custom exception
        }
        catch (ConstraintViolationException e){
            String errorMessage = e.getConstraintViolations().iterator().next().getMessage();
            throw new ServiceConstraintViolationException(errorMessage);
        }
    }

    public TimeSlot updateTimeSlot(TimeSlot timeSlot) {

        TimeSlot insertTimeSlot = getTimeSlotById(timeSlot.getId());
        insertTimeSlot.setStartTime(timeSlot.getStartTime());
        insertTimeSlot.setEndTime(timeSlot.getEndTime());
        insertTimeSlot.setReserved(timeSlot.isReserved());
        if(timeSlot.getGroup() == null)
            throw new ServiceConstraintViolationException("Group Cannot Be Null");
        insertTimeSlot.setGroup(timeGroupService.getTimeGroupById(timeSlot.getGroup().getId()));
        insertTimeSlot.setUser(timeSlot.getUser() != null ?
                (userService.getUserById(timeSlot.getUser().getId())) : null);

        return this.insertTimeSlot(insertTimeSlot);
    }

    public Map<String, Boolean> deleteTimeSlot(Long id){

        TimeSlot timeSlot = getTimeSlotById(id);

        timeSlotRepository.delete(timeSlot);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted", Boolean.TRUE);
        return response;
    }



}
