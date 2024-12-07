package com.example.javatechmidterm.Models;

public class GroupMember {

    private Integer id, userId, groupId;

    public GroupMember() {}

    public GroupMember(Integer id, Integer userId, Integer groupId) {
        this.id = id;
        this.userId = userId;
        this.groupId = groupId;
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

    @Override
    public String toString() {
        return "GroupMember{" +
                "id=" + id +
                ", userId=" + userId +
                ", groupId=" + groupId +
                "} \n";
    }
}
