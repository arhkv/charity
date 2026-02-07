package com.example.charityapi.controller;

import com.example.charityapi.dto.DonationCreateRequest;
import com.example.charityapi.dto.DonationUpdateRequest;
import com.example.charityapi.entity.Donation;
import com.example.charityapi.service.DonationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donations")
public class DonationController {

    private final DonationService service;

    public DonationController(DonationService service) {
        this.service = service;
    }

    @GetMapping
    public List<Donation> all() {
        return service.all();
    }

    @GetMapping("/{id}")
    public Donation get(@PathVariable Long id) {
        return service.get(id);
    }

    @PostMapping
    public Donation create(@RequestBody DonationCreateRequest req) {
        Donation d = new Donation();
        d.setDonorId(req.getDonorId());
        d.setCharityId(req.getCharityId());
        d.setAmount(req.getAmount());
        d.setComment(req.getComment());
        return service.create(d);
    }

    @PutMapping("/{id}")
    public Donation update(@PathVariable Long id, @RequestBody DonationUpdateRequest req) {
        Donation d = new Donation();
        d.setDonorId(req.getDonorId());
        d.setCharityId(req.getCharityId());
        if (req.getAmount() != null) d.setAmount(req.getAmount());
        d.setComment(req.getComment());
        return service.update(id, d);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    // Data pool endpoint (optional): /api/donations/top?minAmount=1000
    @GetMapping("/top")
    public List<Donation> top(@RequestParam long minAmount) {
        return service.topAbove(minAmount);
    }
}
