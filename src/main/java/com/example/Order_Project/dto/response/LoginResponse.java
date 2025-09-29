package com.example.Order_Project.dto.response;

import com.example.Order_Project.Enum.RoleEnum;
public class LoginResponse {

    private Long id;
    private String fullName;
    private String phone;
    private String email;
    private RoleEnum role;

    public LoginResponse(Long id, String fullName, String phone, String email, RoleEnum role) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public RoleEnum getRole() {
        return role;
    }
}