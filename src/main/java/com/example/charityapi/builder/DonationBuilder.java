package com.example.charityapi.builder;

import com.example.charityapi.entity.Donation;

import java.time.LocalDateTime;

public class DonationBuilder {
    private Long donorId;
    private Long charityId;
    private long amount;
    private LocalDateTime donatedAt = LocalDateTime.now();
    private String comment;

    public DonationBuilder donorId(Long donorId) { this.donorId = donorId; return this; }
    public DonationBuilder charityId(Long charityId) { this.charityId = charityId; return this; }
    public DonationBuilder amount(long amount) { this.amount = amount; return this; }
    public DonationBuilder donatedAt(LocalDateTime donatedAt) { this.donatedAt = donatedAt; return this; }
    public DonationBuilder comment(String comment) { this.comment = comment; return this; }

    public Donation build() {
        return new Donation(null, donorId, charityId, amount, donatedAt, comment);
    }
}
