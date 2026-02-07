package com.example.charityapi.entity;

import java.util.Objects;

public class Donor {
    private Long id;
    private String name;
    private String email;

    public Donor() {}

    public Donor(Long id, String name, String email) {
        this.id = id; this.name = name; this.email = email;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override public String toString() {
        return "Donor{id=" + id + ", name='" + name + "', email='" + email + "'}";
    }
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Donor donor)) return false;
        return Objects.equals(id, donor.id);
    }
    @Override public int hashCode() { return Objects.hash(id); }
}
