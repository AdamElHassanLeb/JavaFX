package com.example.demo.Controllers;

import com.example.demo.DTO.DTO_Mappers.TimeGroupMapper;
import com.example.demo.DTO.DTO_Models.TimeGroupDTO;
import com.example.demo.Exceptions.ServiceConstraintViolationException;
import com.example.demo.Models.*;
import com.example.demo.Services.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/timegroup")
public class TimeGroupController {

    private final UserService userService;
    private final TimeGroupService timeGroupService;

    public TimeGroupController(TimeGroupService timeGroupService, UserService userService) { this.timeGroupService = timeGroupService;
        this.userService = userService;
    }

    @GetMapping("/timegroups")
    public ResponseEntity<List<TimeGroupDTO>> getAllTimeGroups() {
        return ResponseEntity.ok(TimeGroupMapper.toDTOList(timeGroupService.getAllTimeGroups()));
    }

    @GetMapping("/timegroupById/{id}")
    public ResponseEntity<TimeGroupDTO> getTimeGroupById(@PathVariable Long id) {
        return ResponseEntity.ok(TimeGroupMapper.toDTO(timeGroupService.getTimeGroupById(id)));
    }

    @GetMapping("/timegroupsByAdmin/{id}")
    public ResponseEntity<List<TimeGroupDTO>> getTimeGroupsByAdmin(@PathVariable Long id){
        User user = userService.getUserById(id);
        return ResponseEntity.ok(TimeGroupMapper.toDTOList(timeGroupService.getTimeGroupsByAdmin(user)));
    }

    @PostMapping("/timegroup")
    public ResponseEntity<?> createTimeGroup(@RequestBody TimeGroupDTO timeGroupDTO) {
        try{
            TimeGroup savedTimeGroup = timeGroupService.insertTimeGroup(TimeGroupMapper.toEntity(timeGroupDTO));
            return new ResponseEntity<TimeGroupDTO>(TimeGroupMapper.toDTO(savedTimeGroup), HttpStatus.CREATED);
        } catch (ServiceConstraintViolationException e) {
            String errorMessage = e.getMessage(); // Get the error message from the exception
            return new ResponseEntity<String>(errorMessage, HttpStatus.CONFLICT);
        }
    }


    @PutMapping("/timegroup")
    public ResponseEntity<?> updateTimeGroup(@Valid @RequestBody TimeGroupDTO timeGroupDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Invalid user data");
        }
        try{
            TimeGroup savedTimeGroup = timeGroupService.updateTimeGroup(TimeGroupMapper.toEntity(timeGroupDTO));
            return ResponseEntity.ok(TimeGroupMapper.toDTO(savedTimeGroup));
        }catch (ServiceConstraintViolationException e) {
            String errorMessage = e.getMessage(); // Get the error message from the exception
            return new ResponseEntity<String>(errorMessage, HttpStatus.CONFLICT);
        }
    }


    //TODO FIX DEPENDECY BY DELETING ALL TIMESLOTS AND GROUP MEMBERS IN TIMEGROUP
    @DeleteMapping("/timegroup/{id}")
    public ResponseEntity<Object> deleteTimeGroup(@PathVariable Long id) {
        timeGroupService.deleteTimeGroup(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/timegroupByNameAndPassword")
    public ResponseEntity<TimeGroupDTO> getTimeGroupByNameAndPassword(@RequestBody JSONAuth auth) {
        return ResponseEntity
                .ok(TimeGroupMapper
                        .toDTO(timeGroupService.getTimeGroupByNameAndPassword(auth.name, auth.password)));
    }

    static class JSONAuth{
        public JSONAuth(){}
        public String name;
        public String password;
    }
}
