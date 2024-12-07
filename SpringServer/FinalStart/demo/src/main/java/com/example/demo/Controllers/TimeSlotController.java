package com.example.demo.Controllers;

import com.example.demo.DTO.DTO_Mappers.TimeGroupMapper;
import com.example.demo.DTO.DTO_Mappers.TimeSlotMapper;
import com.example.demo.DTO.DTO_Models.TimeGroupDTO;
import com.example.demo.DTO.DTO_Models.TimeSlotDTO;
import com.example.demo.Exceptions.ServiceConstraintViolationException;
import com.example.demo.Models.*;
import com.example.demo.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/timeslot")
public class TimeSlotController {

    private final TimeSlotService timeSlotService;
    private final UserService userService;
    private final TimeGroupService timeGroupService;

    @Autowired
    public TimeSlotController(TimeSlotService timeSlotService, UserService userService, TimeGroupService timeGroupService) { this.timeSlotService = timeSlotService;
        this.userService = userService;
        this.timeGroupService = timeGroupService;
    }

    @GetMapping("/timeslots")
    public List<TimeSlotDTO> getTimeSlots() {
        return TimeSlotMapper.toDTOList(timeSlotService.getAllTimeSlots());
    }

    @GetMapping("timeslotById/{id}")
    public TimeSlotDTO getTimeSlotById(@PathVariable Long id) {
        return TimeSlotMapper.toDTO(timeSlotService.getTimeSlotById(id));
    }

    @GetMapping("timeslotsByUser/{id}")
    public List<TimeSlotDTO> getTimeSlotsByUserId(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return TimeSlotMapper.toDTOList(timeSlotService.getTimeSlotsByUser(user));
    }

    @GetMapping("/timeslotsByGroup/{id}")
    public List<TimeSlotDTO> getTimeSlotsByGroup(@PathVariable Long id) {
        TimeGroup timeGroup = timeGroupService.getTimeGroupById(id);
        return TimeSlotMapper.toDTOList(timeSlotService.getTimeSlotsByGroup(timeGroup));
    }

    @GetMapping("/timelotsByGroupAndMonth/{id}/{month}")
    public List<TimeSlotDTO> getTimeSlotsByGroupAndMonth(@PathVariable Long id, @PathVariable int month){
        TimeGroup timeGroup = timeGroupService.getTimeGroupById(id);
        return TimeSlotMapper.toDTOList(timeSlotService.getTimeSlotsByGroupAndMonth(timeGroup,month));
    }

    @GetMapping("/timeslotsByGroupAndDay/{id}/{day}/{month}/{year}")
    public List<TimeSlotDTO> getTimeSlotsByGroupAndDay(@PathVariable Long id,
                                                       @PathVariable int day,
                                                       @PathVariable int month,
                                                       @PathVariable int year){
        TimeGroup timeGroup = timeGroupService.getTimeGroupById(id);
        return TimeSlotMapper.toDTOList(timeSlotService
                .getTimeSlotsByGroupAndDay(timeGroup, day, month, year));
    }

    @PostMapping("/timeslot")
    public ResponseEntity<?> createTimeSlot(@RequestBody TimeSlotDTO timeSlotDTO) {
        try{
            TimeSlot savedTimeSlot = timeSlotService.insertTimeSlot(TimeSlotMapper.toEntity(timeSlotDTO));
            return new ResponseEntity<TimeSlotDTO>(TimeSlotMapper.toDTO(savedTimeSlot), HttpStatus.CREATED);
        } catch (ServiceConstraintViolationException e) {
            String errorMessage = e.getMessage(); // Get the error message from the exception
            return new ResponseEntity<String>(errorMessage, HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/timeslot")
    public ResponseEntity<?> updateTimeSlot(@RequestBody TimeSlotDTO timeSlotDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Invalid timeslot data");
        }
        try{
            TimeSlot timeSlot = timeSlotService.updateTimeSlot(TimeSlotMapper.toEntity(timeSlotDTO));
            return ResponseEntity.ok(TimeSlotMapper.toDTO(timeSlot));
        }catch (ServiceConstraintViolationException e) {
            String errorMessage = e.getMessage(); // Get the error message from the exception
            return new ResponseEntity<String>(errorMessage, HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/timeslot/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        timeSlotService.deleteTimeSlot(id);
        return ResponseEntity.noContent().build();
    }


}
