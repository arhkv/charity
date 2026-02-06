package com.example.charityapi.controller;

import com.example.charityapi.dto.CharityCreateRequest;
import com.example.charityapi.dto.CharityUpdateRequest;
import com.example.charityapi.entity.Charity;
import com.example.charityapi.service.CharityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/charities")
public class CharityController {

    private final CharityService charityService;

    public CharityController(CharityService charityService) {
        this.charityService = charityService;
    }

    @PostMapping
    public Charity create(@RequestBody CharityCreateRequest req) {
        return charityService.create(new Charity(req.getName(), req.getDescription()));
    }

    @GetMapping("/{id}")
    public Charity get(@PathVariable Long id) {
        return charityService.get(id);
    }

    @GetMapping
    public List<Charity> all() {
        return charityService.all();
    }

    @PutMapping("/{id}")
    public Charity update(@PathVariable Long id, @RequestBody CharityUpdateRequest req) {
        Charity current = charityService.get(id);
        if (req.getName() != null) current.setName(req.getName());
        if (req.getDescription() != null) current.setDescription(req.getDescription());
        return charityService.update(id, current);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        charityService.delete(id);
    }
}
