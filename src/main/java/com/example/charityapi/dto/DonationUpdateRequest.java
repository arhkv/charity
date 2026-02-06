package com.example.charityapi.dto;

import java.time.LocalDateTime;

public class DonationUpdateRequest {
    private Long donorId;
    private Long charityId;
    private Long amount; // Long чтобы можно было "не передавать"
    private LocalDateTime donatedAt;
    private String comment;

    public DonationUpdateRequest() {}

    public Long getDonorId() { return donorId; }
    public void setDonorId(Long donorId) { this.donorId = donorId; }

    public Long getCharityId() { return charityId; }
    public void setCharityId(Long charityId) { this.charityId = charityId; }

    public Long getAmount() { return amount; }
    public void setAmount(Long amount) { this.amount = amount; }

    public LocalDateTime getDonatedAt() { return donatedAt; }
    public void setDonatedAt(LocalDateTime donatedAt) { this.donatedAt = donatedAt; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
}
