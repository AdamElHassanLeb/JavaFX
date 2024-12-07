package com.example.demo.DTO.DTO_Mappers;

import com.example.demo.Models.*;
import com.example.demo.DTO.DTO_Models.*;
import com.example.demo.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GroupMemberMapper {

    private static UserService userService;
    private static TimeGroupService timeGroupService;
    @Autowired
    public GroupMemberMapper(UserService userService, TimeGroupService timeGroupService) {
        this.userService = userService;
        this.timeGroupService = timeGroupService;
    }

    public static GroupMemberDTO toDTO(GroupMember groupMember) {
        return new GroupMemberDTO(
                groupMember.getId(),
                groupMember.getUser().getId(),
                groupMember.getGroup().getId()
        );
    }

    public static GroupMember toEntity(GroupMemberDTO dto) {
            GroupMember groupMember = new GroupMember();
            groupMember.setId(dto.getId());
            groupMember.setUser(userService.getUserById(dto.getUserId()));
            groupMember.setGroup(timeGroupService.getTimeGroupById(dto.getGroupId()));
            return groupMember;
    }

    public static List<GroupMemberDTO> toDTOList(List<GroupMember> groupMembers) {
        return groupMembers.stream()
                .map(GroupMemberMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static List<GroupMember> toEntityList(List<GroupMemberDTO> dtos) {
        return dtos.stream()
                .map(GroupMemberMapper::toEntity)
                .collect(Collectors.toList());
    }
}
