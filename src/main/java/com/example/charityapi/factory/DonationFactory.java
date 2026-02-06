package com.example.charityapi.factory;

import com.example.charityapi.builder.DonationBuilder;
import com.example.charityapi.dto.DonationCreateRequest;
import com.example.charityapi.entity.Donation;

public class DonationFactory {
    public static Donation fromRequest(DonationCreateRequest req) {
        return new DonationBuilder()
                .donorId(req.getDonorId())
                .charityId(req.getCharityId())
                .amount(req.getAmount())
                .donatedAt(req.getDonatedAt())
                .comment(req.getComment())
                .build();
    }
}
