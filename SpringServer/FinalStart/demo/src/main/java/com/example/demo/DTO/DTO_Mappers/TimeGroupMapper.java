package com.example.demo.DTO.DTO_Mappers;

import com.example.demo.Models.*;
import com.example.demo.DTO.DTO_Models.*;
import com.example.demo.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TimeGroupMapper {

    private static UserService userService;

    @Autowired
    public TimeGroupMapper(UserService userService) {
        TimeGroupMapper.userService = userService;
    }

    public static TimeGroupDTO toDTO(TimeGroup timeGroup) {
        return new TimeGroupDTO(
                timeGroup.getId(),
                timeGroup.getAdmin().getId(),
                timeGroup.getName(),
                timeGroup.getPassword()
        );
    }

    public static TimeGroup toEntity(TimeGroupDTO dto) {
       TimeGroup timeGroup = new TimeGroup();
       timeGroup.setId(dto.getId());
       timeGroup.setPassword(dto.getPassword());
       timeGroup.setName(dto.getName());
       timeGroup.setAdmin(userService.getUserById(dto.getAdminId()));
        return timeGroup;
    }

    public static List<TimeGroupDTO> toDTOList(List<TimeGroup> timeGroups) {
        return timeGroups.stream()
                .map(TimeGroupMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static List<TimeGroup> toEntityList(List<TimeGroupDTO> dtos) {
        return dtos.stream()
                .map(TimeGroupMapper::toEntity)
                .collect(Collectors.toList());
    }
}
