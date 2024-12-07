package com.example.demo.DTO.DTO_Models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class TimeSlotDTO {
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss a");
    private Long id, userId, groupId;
    private LocalDateTime startTime, endTime;
    @JsonProperty("isReserved")
    private boolean isReserved;
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");

    public TimeSlotDTO(Long id, Long userId, Long groupId, LocalDateTime startTime, LocalDateTime endTime, boolean isReserved) {

        this.id = id;
        this.userId = userId;
        this.groupId = groupId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isReserved = isReserved;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public LocalDateTime getStartTime() {return startTime;}

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public boolean getisReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }

    public String getAmPmStartTime(){
        return this.startTime.format(formatter);
    }

    public String getAmPmEndTime(){
        return this.endTime.format(formatter);
    }

    @Override
    public String toString() {
        return "TimeSlot{" +
                "id=" + id +
                ", userId=" + userId +
                ", groupId=" + groupId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", getisReserved=" + isReserved +
                "} \n";
    }
}
