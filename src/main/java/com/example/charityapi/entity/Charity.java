package com.example.charityapi.entity;

import java.util.Objects;

public class Charity {
    private Long id;
    private String name;
    private String description;

    public Charity() {}

    public Charity(Long id, String name, String description) {
        this.id = id; this.name = name; this.description = description;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @Override public String toString() {
        return "Charity{id=" + id + ", name='" + name + "', description='" + description + "'}";
    }
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Charity charity)) return false;
        return Objects.equals(id, charity.id);
    }
    @Override public int hashCode() { return Objects.hash(id); }
}
