package com.example.charityapi.controller;

import com.example.charityapi.dto.DonationCreateRequest;
import com.example.charityapi.dto.DonationResponse;
import com.example.charityapi.entity.Donation;
import com.example.charityapi.factory.DonationFactory;
import com.example.charityapi.service.DonationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donations")
public class DonationController {

    private final DonationService donationService;

    public DonationController(DonationService donationService) {
        this.donationService = donationService;
    }

    @PostMapping
    public DonationResponse create( @RequestBody DonationCreateRequest req) {
        Donation donation = DonationFactory.fromRequest(req); // Factory + Builder
        Donation saved = donationService.create(donation);
        return toResponse(saved);
    }

    @GetMapping("/{id}")
    public DonationResponse get(@PathVariable Long id) {
        return toResponse(donationService.get(id));
    }

    @GetMapping
    public List<DonationResponse> all() {
        return donationService.all().stream().map(this::toResponse).toList();
    }

    @GetMapping("/by-donor/{donorId}")
    public List<DonationResponse> byDonor(@PathVariable Long donorId) {
        return donationService.byDonor(donorId).stream().map(this::toResponse).toList();
    }

    @GetMapping("/by-charity/{charityId}")
    public List<DonationResponse> byCharity(@PathVariable Long charityId) {
        return donationService.byCharity(charityId).stream().map(this::toResponse).toList();
    }

    @GetMapping("/top")
    public List<DonationResponse> topAbove(@RequestParam long minAmount) {
        return donationService.topAbove(minAmount).stream().map(this::toResponse).toList();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        donationService.delete(id);
    }

    private DonationResponse toResponse(Donation d) {
        return new DonationResponse(
                d.getId(),
                d.getDonorId(),
                d.getCharityId(),
                d.getAmount(),
                d.getDonatedAt(),
                d.getComment()
        );
    }
}
