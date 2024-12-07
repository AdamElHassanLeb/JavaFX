package com.example.demo.Repositories;

import com.example.demo.Models.*;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TimeGroupRepository extends JpaRepository<TimeGroup, Long> {

    @Override
    @Query("SELECT t FROM TimeGroup t")
    public List<TimeGroup> findAll();
    public Optional<TimeGroup> getTimeGroupByNameAndPassword(String name, String password);
    public Optional<List<TimeGroup>> getTimeGroupByAdmin(@NotEmpty User admin);
}
