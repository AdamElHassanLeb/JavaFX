package com.example.demo.Repositories;

import com.example.demo.Models.*;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {

    public Optional<List<GroupMember>> findGroupMemberByGroup(@NotEmpty TimeGroup group);
    public Optional<List<GroupMember>> findGroupMemberByUser(@NotEmpty User user);
    public List<GroupMember> findGroupMemberByUserAndGroup(@NotEmpty User user, @NotEmpty TimeGroup group);
}
