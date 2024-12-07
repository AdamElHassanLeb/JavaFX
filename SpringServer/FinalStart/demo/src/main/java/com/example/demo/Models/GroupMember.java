package com.example.demo.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "groupmember")
public class GroupMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_member_id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "group_member_user_id", nullable = false)
    private User user;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "group_member_group_id", nullable = false)
    private TimeGroup group;

    public GroupMember() {}

    public GroupMember(User user, TimeGroup group) {
        this.user = user;
        this.group = group;
    }

    public GroupMember(Long id, User user, TimeGroup group) {
        this.id = id;
        this.user = user;
        this.group = group;
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

    @Override
    public String toString() {
        return "GroupMember{" +
                "id=" + id +
                ", user=" + user +
                ", group=" + group +
                '}';
    }
}
