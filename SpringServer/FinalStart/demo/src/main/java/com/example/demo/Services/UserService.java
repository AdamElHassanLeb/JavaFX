package com.example.demo.Services;

import com.example.demo.Exceptions.*;
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
public class UserService {

    private final UserRepository userRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final TimeGroupRepository timeGroupRepository;

    @Autowired
    public UserService(UserRepository userRepository, GroupMemberRepository groupMemberRepository, TimeSlotRepository timeSlotRepository, TimeGroupRepository timeGroupRepository) { this.userRepository = userRepository;
        this.groupMemberRepository = groupMemberRepository;
        this.timeSlotRepository = timeSlotRepository;
        this.timeGroupRepository = timeGroupRepository;
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public User getUserById(long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public User insertUser(User newUser){
        try{
        return userRepository.save(newUser);
        }catch (DataIntegrityViolationException e){
            String errorMessage = e.getRootCause().getMessage(); // Extract the error message
            throw new ServiceConstraintViolationException(errorMessage); // Wrap the error message in a custom exception
        }
        catch (ConstraintViolationException e){
            String errorMessage = e.getConstraintViolations().iterator().next().getMessage();
            throw new ServiceConstraintViolationException(errorMessage);
        }
    }

    public User updateUser(User newUser){
        User user = userRepository.findById(newUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setUsername(newUser.getUsername());
        user.setPassword(newUser.getPassword());
        user.setFirstName(newUser.getFirstName());
        user.setLastName(newUser.getLastName());
        user.setPhoneNumber(newUser.getPhoneNumber());

        return insertUser(user);
    }

    public Map<String, Boolean> deleteUser(long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<GroupMember> groupMembers = groupMemberRepository
                .findGroupMemberByUser(user).orElse(new ArrayList<GroupMember>());

        groupMemberRepository.deleteAll(groupMembers);

        List<TimeSlot> timeSlots = timeSlotRepository
                .getTimeSlotsByUser(user).orElse(new ArrayList<TimeSlot>());

        for(TimeSlot timeSlot : timeSlots){
            timeSlot.setUser(null);
            timeSlot.setReserved(false);
            timeSlotRepository.save(timeSlot);
        }

        List<TimeGroup> timeGroups = timeGroupRepository.getTimeGroupByAdmin(user)
                .orElse(new ArrayList<>());

        for(TimeGroup timeGroup : timeGroups){
            deleteTGForCircular(timeGroup);
        }

        userRepository.delete(user);

        Map<String, Boolean> response = new HashMap<>();
        response.put("delete", Boolean.TRUE);
        return response;
    }

    public User authUser(String username, String password){
       return userRepository.findByUsernameAndPassword(username, password)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private void deleteTGForCircular(TimeGroup timeGroup){
        List<TimeSlot> timeSlots = timeSlotRepository
                .getTimeSlotsByGroup(timeGroup).orElse(new ArrayList<TimeSlot>());
        List<GroupMember> groupMembers = groupMemberRepository
                .findGroupMemberByGroup(timeGroup).orElse(new ArrayList<GroupMember>());

        groupMemberRepository.deleteAll(groupMembers);
        timeSlotRepository.deleteAll(timeSlots);

        timeGroupRepository.delete(timeGroup);
    }
}
