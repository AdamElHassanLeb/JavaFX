package com.example.demo.Repositories;

import com.example.demo.Models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {

    Optional<List<TimeSlot>> getTimeSlotsByUser(User user);
    Optional<List<TimeSlot>> getTimeSlotsByGroup(TimeGroup timeGroup);

    @Query("SELECT t FROM TimeSlot t WHERE t.group = :group AND MONTH(t.startTime) = :month")
    Optional<List<TimeSlot>> getTimeSlotsByGroupAndMonth(@Param("group") TimeGroup group, @Param("month") int month);

    @Query("SELECT t FROM TimeSlot t WHERE t.group = :group AND DAY(t.startTime) = :day AND MONTH(t.startTime) = :month AND YEAR(t.startTime) = :year")
    Optional<List<TimeSlot>> getTimeSlotsByGroupAndDay(@Param("group") TimeGroup group, @Param("day") int day, @Param("month") int month, @Param("year") int year);
}
