package com.example.charityapi.service;

import com.example.charityapi.entity.Charity;
import com.example.charityapi.exception.NotFoundException;
import com.example.charityapi.repository.CharityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharityService {
    private final CharityRepository repo;

    public CharityService(CharityRepository repo) {
        this.repo = repo;
    }

    public Charity create(Charity charity) {
        return repo.save(charity);
    }

    public Charity get(Long id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("Charity not found: id=" + id));
    }

    public List<Charity> all() {
        return repo.findAll();
    }

    public Charity update(Long id, Charity charity) {
        get(id);
        return repo.update(id, charity);
    }

    public void delete(Long id) {
        get(id);
        repo.deleteById(id);
    }
}
