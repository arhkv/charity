package com.example.charityapi.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Donor {
    private Long id;
    private String name;
    private String email;
    private LocalDateTime createdAt;

    public Donor() {}

    public Donor(Long id, String name, String email, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }

    public Donor(String name, String email) {
        this(null, name, email, null);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "Donor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

    // identity: id (если есть), иначе email
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Donor donor)) return false;
        if (id != null && donor.id != null) return Objects.equals(id, donor.id);
        return Objects.equals(email, donor.email);
    }

    @Override
    public int hashCode() {
        return (id != null) ? Objects.hash(id) : Objects.hash(email);
    }
}
