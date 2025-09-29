package com.example.Order_Project.dto.response;

import com.example.Order_Project.Enum.RoleEnum;

public class RegisterResponse {

    private String fullName;
    private String phone;
    private String email;
    private RoleEnum role;

    public RegisterResponse() {
    }

    public RegisterResponse(String fullName, String phone, String email, RoleEnum role) {
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.role = role;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }
}
