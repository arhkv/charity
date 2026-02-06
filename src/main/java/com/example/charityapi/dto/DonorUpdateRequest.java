package com.example.charityapi.dto;

public class DonorUpdateRequest {
    private String name;
    private String email;

    public DonorUpdateRequest() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
