package com.example.charityapi.controller;

import com.example.charityapi.dto.DonorCreateRequest;
import com.example.charityapi.dto.DonorUpdateRequest;
import com.example.charityapi.entity.Donor;
import com.example.charityapi.service.DonorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donors")
public class DonorController {

    private final DonorService donorService;

    public DonorController(DonorService donorService) {
        this.donorService = donorService;
    }

    @PostMapping
    public Donor create( @RequestBody DonorCreateRequest req) {
        return donorService.create(new Donor(req.getName(), req.getEmail()));
    }

    @GetMapping("/{id}")
    public Donor get(@PathVariable Long id) {
        return donorService.get(id);
    }

    @GetMapping
    public List<Donor> all() {
        return donorService.all();
    }

    @PutMapping("/{id}")
    public Donor update(@PathVariable Long id, @RequestBody DonorUpdateRequest req) {
        Donor current = donorService.get(id);
        if (req.getName() != null) current.setName(req.getName());
        if (req.getEmail() != null) current.setEmail(req.getEmail());
        return donorService.update(id, current);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        donorService.delete(id);
    }
}
