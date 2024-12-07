package com.example.demo.Services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.demo.Exceptions.ResourceNotFoundException;
import com.example.demo.Exceptions.ServiceConstraintViolationException;
import com.example.demo.Models.*;
import com.example.demo.Repositories.*;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class GroupMemberService {

    private final UserService userService;
    private final GroupMemberRepository groupMemberRepository;
    private final TimeGroupService timeGroupService;

    @Autowired
    public GroupMemberService(UserService userService, GroupMemberRepository groupMemberRepository, TimeGroupService timeGroupService) {
        this.userService = userService;
        this.groupMemberRepository = groupMemberRepository;
        this.timeGroupService = timeGroupService;
    }

    public List<GroupMember> getGroupMembers(){
        return groupMemberRepository.findAll();
    }

    public GroupMember getGroupMemberById(Long id){
        return groupMemberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Group Member not found"));
    }

    public List<GroupMember> getGroupMembersByTimeGroup(Long id){

        TimeGroup timeGroup = timeGroupService.getTimeGroupById(id);

        return groupMemberRepository.findGroupMemberByGroup(timeGroup)
                .orElseThrow(() -> new ResourceNotFoundException("Group Member not found"));
    }

    public List<GroupMember> getGroupMembersByUser(Long id){
        User user = userService.getUserById(id);
        return groupMemberRepository.findGroupMemberByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Group Member not found"));
    }

    public GroupMember insertGroupMember(GroupMember groupMember){
        try{

            if(!groupMemberRepository.findGroupMemberByUserAndGroup
                    (groupMember.getUser(), groupMember.getGroup())
                    .isEmpty()){
                throw new ServiceConstraintViolationException("Group Member already exists");
            }

            groupMember.setUser(userService.getUserById(groupMember.getUser().getId()));
            groupMember.setGroup(timeGroupService.getTimeGroupById(groupMember.getGroup().getId()));
            return groupMemberRepository.save(groupMember);
        }catch (DataIntegrityViolationException e){
            String errorMessage = e.getRootCause().getMessage(); // Extract the error message
            throw new ServiceConstraintViolationException(errorMessage); // Wrap the error message in a custom exception
        }
        catch (ConstraintViolationException e){
            String errorMessage = e.getConstraintViolations().iterator().next().getMessage();
            throw new ServiceConstraintViolationException(errorMessage);
        }
    }

    public GroupMember updateGroupMember(GroupMember groupMember){

        GroupMember grpmbr = getGroupMemberById(groupMember.getId());

        grpmbr.setUser(groupMember.getUser());
        grpmbr.setGroup(groupMember.getGroup());

        return insertGroupMember(grpmbr);
    }

    public Map<String, Boolean> deleteGroupMember(Long id){
        GroupMember groupMember = getGroupMemberById(id);

        groupMemberRepository.delete(groupMember);

        Map<String, Boolean> response = new HashMap<>();
        response.put("delete", Boolean.TRUE);
        return response;
    }


}

