package com.example.demo.DTO.DTO_Models;

public class TimeGroupDTO {

    private Long id, adminId;
    private String name, password;


    public TimeGroupDTO(Long id, Long adminId, String name, String password) {
        this.id = id;
        this.adminId = adminId;
        this.name = name;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
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
                ", adminId=" + adminId +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                "} \n";
    }
}
