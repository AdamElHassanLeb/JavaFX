package com.example.demo.DTO.DTO_Mappers;

import com.example.demo.Exceptions.ServiceConstraintViolationException;
import com.example.demo.Models.*;
import com.example.demo.DTO.DTO_Models.*;
import com.example.demo.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TimeSlotMapper {

    private static UserService userService;
    private static TimeGroupService timeGroupService;

    @Autowired
    public TimeSlotMapper(UserService userService, TimeGroupService timeGroupService) {
        this.userService = userService;
        this.timeGroupService = timeGroupService;
    }

    public static TimeSlotDTO toDTO(TimeSlot timeSlot) {
        return new TimeSlotDTO(
                timeSlot.getId(),
                timeSlot.getUser() != null ? timeSlot.getUser().getId() : null,
                timeSlot.getGroup().getId() != null ? timeSlot.getGroup().getId() : null,
                timeSlot.getStartTime(),
                timeSlot.getEndTime(),
                timeSlot.isReserved()
        );
    }

    public static TimeSlot toEntity(TimeSlotDTO dto) {
        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setId(dto.getId());
        timeSlot.setStartTime(dto.getStartTime());
        timeSlot.setEndTime(dto.getEndTime());
        timeSlot.setReserved(dto.getisReserved());

        if(dto.getGroupId()  == null)
            throw new ServiceConstraintViolationException("Group is required");
        timeSlot.setGroup(timeGroupService.getTimeGroupById(dto.getGroupId()));

// Set user only if userId is not null
        timeSlot.setUser(dto.getUserId() != null ?
                userService.getUserById(dto.getUserId()) : null);

        return timeSlot;
    }

    public static List<TimeSlotDTO> toDTOList(List<TimeSlot> timeSlots) {
        return timeSlots.stream()
                .map(TimeSlotMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static List<TimeSlot> toEntityList(List<TimeSlotDTO> dtos) {
        return dtos.stream()
                .map(TimeSlotMapper::toEntity)
                .collect(Collectors.toList());
    }
}
