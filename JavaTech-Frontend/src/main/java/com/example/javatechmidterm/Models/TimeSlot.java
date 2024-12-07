package com.example.javatechmidterm.Models;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class TimeSlot {
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss a");
    private Integer id, userId, groupId;
    private LocalDateTime startTime, endTime;
    private boolean isReserved;
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");

    public TimeSlot() {}

    public TimeSlot(Integer id, Integer userId, Integer groupId, LocalDateTime startTime, LocalDateTime endTime, boolean isReserved) {

        this.id = id;
        this.userId = userId;
        this.groupId = groupId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isReserved = isReserved;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
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

    public boolean isReserved() {
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
                ", isReserved=" + isReserved +
                "} \n";
    }
}
