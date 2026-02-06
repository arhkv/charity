package com.example.charityapi.dto;

public class CharityCreateRequest {
    private String name;
    private String description;

    public CharityCreateRequest() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
