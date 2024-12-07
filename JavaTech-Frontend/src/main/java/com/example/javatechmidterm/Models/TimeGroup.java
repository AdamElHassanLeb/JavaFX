package com.example.javatechmidterm.Models;

public class TimeGroup {

    private Integer id, adminId;
    private String name, password;


    public TimeGroup(){}

    public TimeGroup(Integer id, Integer adminId, String name, String password) {
        this.id = id;
        this.adminId = adminId;
        this.name = name;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
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
