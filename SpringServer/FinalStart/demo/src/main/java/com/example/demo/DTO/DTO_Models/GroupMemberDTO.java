package com.example.demo.DTO.DTO_Models;

public class GroupMemberDTO {

    private Long id, userId, groupId;

    public GroupMemberDTO(Long id, Long userId, Long groupId) {
        this.id = id;
        this.userId = userId;
        this.groupId = groupId;
    }

    public Long getId() {
        return id;
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

    @Override
    public String toString() {
        return "GroupMember{" +
                "id=" + id +
                ", userId=" + userId +
                ", groupId=" + groupId +
                "} \n";
    }
}
