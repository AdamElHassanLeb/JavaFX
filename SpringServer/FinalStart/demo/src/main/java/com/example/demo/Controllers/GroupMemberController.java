package com.example.demo.Controllers;

import com.example.demo.Exceptions.ServiceConstraintViolationException;
import com.example.demo.Models.*;
import com.example.demo.Services.*;
import com.example.demo.Utils.*;
import com.example.demo.DTO.DTO_Models.*;
import com.example.demo.DTO.DTO_Mappers.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("api/groupMember")
public class GroupMemberController {

    private final GroupMemberService groupMemberService;

    public GroupMemberController(GroupMemberService groupMemberService) { this.groupMemberService = groupMemberService; }

    @GetMapping("groupMembers")
    public ResponseEntity<List<GroupMemberDTO>> getAllGroupMembers() {
        return ResponseEntity.ok(GroupMemberMapper.toDTOList(groupMemberService.getGroupMembers()));
    }

    @GetMapping("/groupMemberById/{id}")
    public ResponseEntity<GroupMemberDTO> getGroupMemberById(@PathVariable Long id) {
        return ResponseEntity.ok(GroupMemberMapper.toDTO(groupMemberService.getGroupMemberById(id)));
    }

    @GetMapping("/groupMembersByGroup/{id}")
    public ResponseEntity<List<GroupMemberDTO>> getGroupMembersByGroup(@PathVariable Long id) {
        return ResponseEntity.ok(GroupMemberMapper.toDTOList(groupMemberService.getGroupMembersByTimeGroup(id)));
    }

    @GetMapping("/groupMembersByUser/{id}")
    public ResponseEntity<List<GroupMemberDTO>> getGroupMembersByUser(@PathVariable Long id) {
        return ResponseEntity.ok(GroupMemberMapper.toDTOList(groupMemberService.getGroupMembersByUser(id)));
    }

    @PostMapping("groupMember")
    public ResponseEntity<?> createGroupMember(@RequestBody GroupMemberDTO groupMemberDTO) {
        try{
            GroupMember savedGroupMember = groupMemberService.insertGroupMember(GroupMemberMapper.toEntity(groupMemberDTO));
            return new ResponseEntity<GroupMemberDTO>(GroupMemberMapper.toDTO(savedGroupMember), HttpStatus.CREATED);
        }catch (ServiceConstraintViolationException e) {
            String errorMessage = e.getMessage(); // Get the error message from the exception
            return new ResponseEntity<String>(errorMessage, HttpStatus.CONFLICT);
        }
    }

    @PutMapping("groupMember")
    public ResponseEntity<?> updateGroupMember(@Valid @RequestBody GroupMemberDTO groupMemberDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Invalid group member data");
        }
        try {
            GroupMember updatedGroupMember = groupMemberService
                    .updateGroupMember(GroupMemberMapper.toEntity(groupMemberDTO));
            return ResponseEntity.ok(GroupMemberMapper.toDTO(updatedGroupMember));
        }catch (ServiceConstraintViolationException e) {
            String errorMessage = e.getMessage(); // Get the error message from the exception
            return new ResponseEntity<String>(errorMessage, HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("groupMember/{id}")
    public ResponseEntity<Object> deleteGroupMember(@PathVariable Long id){
        groupMemberService.deleteGroupMember(id);
        return ResponseEntity.noContent().build();
    }





}
