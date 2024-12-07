package com.example.demo.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "timegroup")
public class TimeGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "timegroup_id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "timegroup_admin_id")
    private User admin;

    @NotNull
    @Column(name = "timegroup_name", unique = true)
    private String name;

    @NotNull
    @Column(name = "timegroup_password")
    private String password;

    //@OneToMany(mappedBy = "group")
    //@JsonIgnore
    //private List<TimeSlot> timeSlots;

    public TimeGroup() {}

    public TimeGroup(User admin, String name, String password) {
        this.admin = admin;
        this.name = name;
        this.password = password;
        //this.timeSlots = timeSlots;
    }

    public TimeGroup(Long id, User admin, String name, String password) {
        this.id = id;
        this.admin = admin;
        this.name = name;
        this.password = password;
        //this.timeSlots = timeSlots;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "TimeGroup{" +
                "id=" + id +
                ", admin=" + admin +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
