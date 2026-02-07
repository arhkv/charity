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

    private final DonorService service;

    public DonorController(DonorService service) {
        this.service = service;
    }

    @GetMapping
    public List<Donor> all() {
        return service.all();
    }

    @GetMapping("/{id}")
    public Donor get(@PathVariable Long id) {
        return service.get(id);
    }

    @PostMapping
    public Donor create(@RequestBody DonorCreateRequest req) {
        Donor d = new Donor(null, req.getName(), req.getEmail());
        return service.create(d);
    }

    @PutMapping("/{id}")
    public Donor update(@PathVariable Long id, @RequestBody DonorUpdateRequest req) {
        Donor d = new Donor(null, req.getName(), req.getEmail());
        return service.update(id, d);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
