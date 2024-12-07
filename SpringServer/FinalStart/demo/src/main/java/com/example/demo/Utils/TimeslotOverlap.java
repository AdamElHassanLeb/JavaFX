package com.example.demo.Utils;
import com.example.demo.Models.*;
import com.example.demo.Services.*;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class TimeslotOverlap {

    static TimeSlotService timeSlotService;

    public TimeslotOverlap(TimeSlotService timeSlotService) {
        this.timeSlotService = timeSlotService;
    }

    public static boolean isTimeSlotOverlap(TimeSlot newTimeSlot) {
        List<TimeSlot> existingTimeSlots = timeSlotService.getTimeSlotsByGroup(newTimeSlot.getGroup());

        for (TimeSlot existingTimeSlot : existingTimeSlots) {
            if (existingTimeSlot.getId() != null && existingTimeSlot.getId().equals(newTimeSlot.getId())) {
                // Skip comparing with itself
                continue;
            }

            boolean startTimeOverlap = newTimeSlot.getStartTime().isBefore(existingTimeSlot.getEndTime()) &&
                    existingTimeSlot.getStartTime().isBefore(newTimeSlot.getEndTime());

            if (startTimeOverlap) {
                return true; // Overlapping time slot found
            }
        }

        return false; // No overlapping time slot found
    }
}
