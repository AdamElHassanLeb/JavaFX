package com.example.demo.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "timeslot")
public class TimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "timeslot_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "timeslot_user_id")
    private User user;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "timeslot_group_id", nullable = false)
    private TimeGroup group;

    @NotNull
    //@Convert(converter = LocalDateTimeConverter.class)
    @Column(name = "timeslot_start_time", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime startTime;

    @NotNull
    //@Convert(converter = LocalDateTimeConverter.class)
    @Column(name = "timeslot_end_time", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime endTime;

    @NotNull
    @Column(name = "timeslot_isReserved", nullable = false)
    private boolean isReserved;

    public TimeSlot() {}

    public TimeSlot(User user, TimeGroup group, LocalDateTime startTime, LocalDateTime endTime, boolean isReserved) {
        this.user = user;
        this.group = group;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isReserved = isReserved;
    }

    public TimeSlot(Long id, User user, TimeGroup group, LocalDateTime startTime, LocalDateTime endTime, boolean isReserved) {
        this.id = id;
        this.user = user;
        this.group = group;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isReserved = isReserved;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TimeGroup getGroup() {
        return group;
    }

    public void setGroup(TimeGroup group) {
        this.group = group;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

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

    public void setReserved(boolean isReserved) {
        this.isReserved = isReserved;
    }

    @Override
    public String toString() {
        return "TimeSlot{" +
                "id=" + id +
                ", user=" + user +
                ", group=" + group +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", getisReserved=" + isReserved +
                '}';
    }
}
