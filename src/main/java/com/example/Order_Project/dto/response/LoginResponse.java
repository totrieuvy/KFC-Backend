package com.example.Order_Project.dto.response;

import com.example.Order_Project.Enum.RoleEnum;
public class LoginResponse {

    private Long id;
    private String fullName;
    private String phone;
    private String email;
    private RoleEnum role;
    private String token;

    public LoginResponse() {
    }

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

    public void setId(Long id) {
        this.id = id;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}