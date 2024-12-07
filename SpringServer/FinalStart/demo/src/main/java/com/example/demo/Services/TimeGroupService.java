package com.example.demo.Services;

import com.example.demo.Exceptions.ResourceNotFoundException;
import com.example.demo.Exceptions.ServiceConstraintViolationException;
import com.example.demo.Models.*;
import com.example.demo.Repositories.*;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TimeGroupService {

    private final UserService userService;
    private final TimeSlotRepository timeSlotRepository;
    private final GroupMemberRepository groupMemberRepository;
    private TimeGroupRepository timeGroupRepository;

    @Autowired
    public TimeGroupService(TimeGroupRepository timeGroupRepository, UserService userService, TimeSlotRepository timeSlotRepository, GroupMemberRepository groupMemberRepository) { this.timeGroupRepository = timeGroupRepository;
        this.userService = userService;
        this.timeSlotRepository = timeSlotRepository;
        this.groupMemberRepository = groupMemberRepository;
    }

    public List<TimeGroup> getAllTimeGroups() { return timeGroupRepository.findAll(); }

    public TimeGroup getTimeGroupById(long id) {
        return timeGroupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Time Group not found"));
    }

    public TimeGroup getTimeGroupByNameAndPassword( String name, String password){
        return timeGroupRepository.getTimeGroupByNameAndPassword(name, password)
                .orElseThrow(() -> new ResourceNotFoundException("Time Group not found"));
    }

    public List<TimeGroup> getTimeGroupsByAdmin(User admin){

        return timeGroupRepository.getTimeGroupByAdmin(admin)
                .orElseThrow(() -> new ResourceNotFoundException
                        ("User is not an admin of any time groups"));
    }

    public TimeGroup insertTimeGroup(TimeGroup timeGroup) {
        try{
            User user = userService.getUserById(timeGroup.getAdmin().getId());
            timeGroup.setAdmin(user);
            return timeGroupRepository.save(timeGroup);
        }
        catch (DataIntegrityViolationException e){
            String errorMessage = e.getRootCause().getMessage(); // Extract the error message
            throw new ServiceConstraintViolationException(errorMessage); // Wrap the error message in a custom exception
        }
        catch (ConstraintViolationException e){
            String errorMessage = e.getConstraintViolations().iterator().next().getMessage();
            throw new ServiceConstraintViolationException(errorMessage);
        }
    }

    public TimeGroup updateTimeGroup(TimeGroup newTimeGroup) {
        TimeGroup timeGroup = getTimeGroupById(newTimeGroup.getId());

        timeGroup.setName(newTimeGroup.getName());
        timeGroup.setPassword(newTimeGroup.getPassword());
        //timeGroup.setAdmin(newTimeGroup.getAdmin());

        return insertTimeGroup(timeGroup);
    }

    public Map<String, Boolean> deleteTimeGroup(long id) {
        TimeGroup timeGroup = getTimeGroupById(id);

        List<TimeSlot> timeSlots = timeSlotRepository
                .getTimeSlotsByGroup(timeGroup).orElse(new ArrayList<TimeSlot>());
        List<GroupMember> groupMembers = groupMemberRepository
                .findGroupMemberByGroup(timeGroup).orElse(new ArrayList<GroupMember>());

        groupMemberRepository.deleteAll(groupMembers);
        timeSlotRepository.deleteAll(timeSlots);

        timeGroupRepository.delete(timeGroup);

        Map<String, Boolean> response = new HashMap<>();
        response.put("delete", Boolean.TRUE);
        return response;
    }
}
