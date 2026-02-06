package com.example.charityapi.dto;

import java.time.LocalDateTime;

public class DonationCreateRequest {
    private Long donorId;
    private Long charityId;
    private long amount;
    private LocalDateTime donatedAt; // можно не передавать - тогда будет now()
    private String comment;

    public DonationCreateRequest() {}

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
}
