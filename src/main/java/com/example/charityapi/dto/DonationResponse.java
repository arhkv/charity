package com.example.charityapi.dto;

import java.time.LocalDateTime;

public class DonationResponse {
    private Long id;
    private Long donorId;
    private Long charityId;
    private long amount;
    private LocalDateTime donatedAt;
    private String comment;

    public DonationResponse() {}

    public DonationResponse(Long id, Long donorId, Long charityId, long amount,
                            LocalDateTime donatedAt, String comment) {
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
}
