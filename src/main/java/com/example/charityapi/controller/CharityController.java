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

    private final CharityService service;

    public CharityController(CharityService service) {
        this.service = service;
    }

    @GetMapping
    public List<Charity> all() {
        return service.all();
    }

    @GetMapping("/{id}")
    public Charity get(@PathVariable Long id) {
        return service.get(id);
    }

    @PostMapping
    public Charity create(@RequestBody CharityCreateRequest req) {
        Charity c = new Charity(null, req.getName(), req.getDescription());
        return service.create(c);
    }

    @PutMapping("/{id}")
    public Charity update(@PathVariable Long id, @RequestBody CharityUpdateRequest req) {
        Charity c = new Charity(null, req.getName(), req.getDescription());
        return service.update(id, c);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
