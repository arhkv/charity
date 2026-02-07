package com.example.charityapi.service;

import com.example.charityapi.entity.Donor;
import com.example.charityapi.exception.NotFoundException;
import com.example.charityapi.repository.DonorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonorService {
    private final DonorRepository repo;

    public DonorService(DonorRepository repo) {
        this.repo = repo;
    }

    public Donor create(Donor donor) {
        return repo.save(donor);
    }

    public Donor get(Long id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("Donor not found: id=" + id));
    }

    public List<Donor> all() {
        return repo.findAll();
    }

    public Donor update(Long id, Donor donor) {
        get(id);
        return repo.update(id, donor);
    }

    public void delete(Long id) {
        get(id);
        repo.deleteById(id);
    }
}
