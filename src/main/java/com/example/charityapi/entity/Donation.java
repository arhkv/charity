package com.example.charityapi.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Donation {
    private Long id;
    private Long donorId;
    private Long charityId;
    private long amount;
    private LocalDateTime donatedAt;
    private String comment;

    public Donation() {}

    public Donation(Long id, Long donorId, Long charityId, long amount, LocalDateTime donatedAt, String comment) {
        this.id = id;
        this.donorId = donorId;
        this.charityId = charityId;
        this.amount = amount;
        this.donatedAt = donatedAt;
        this.comment = comment;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getDonorId() { return donorId; }
    public void setDonorId(Long donorId) { this.donorId = donorId; }

    public Long getCharityId() { return charityId; }
    public void setCharityId(Long charityId) { this.charityId = charityId; }

    public long getAmount() { return amount; }
    public void setAmount(long amount) { this.amount = amount; }

    public LocalDateTime getDonatedAt() { return donatedAt; }
    public void setDonatedAt(LocalDateTime donatedAt) { this.donatedAt = donatedAt; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    @Override
    public String toString() {
        return "Donation{" +
                "id=" + id +
                ", donorId=" + donorId +
                ", charityId=" + charityId +
                ", amount=" + amount +
                ", donatedAt=" + donatedAt +
                ", comment='" + comment + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Donation donation)) return false;
        return Objects.equals(id, donation.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
