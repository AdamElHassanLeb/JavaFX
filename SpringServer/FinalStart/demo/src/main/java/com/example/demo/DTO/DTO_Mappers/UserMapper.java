package com.example.demo.DTO.DTO_Mappers;

import com.example.demo.Models.*;
import com.example.demo.DTO.DTO_Models.*;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber()
        );
    }

    public static User toEntity(UserDTO dto) {
        return new User(
                (long) dto.getId(),
                dto.getUsername(),
                dto.getPassword(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getPhoneNumber()
        );
    }

    public static List<UserDTO> toDTOList(List<User> users) {
        return users.stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static List<User> toEntityList(List<UserDTO> dtos) {
        return dtos.stream()
                .map(UserMapper::toEntity)
                .collect(Collectors.toList());
    }
}
