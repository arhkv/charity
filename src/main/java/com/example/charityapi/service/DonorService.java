package com.example.charityapi.service;

import com.example.charityapi.entity.Donor;
import com.example.charityapi.exception.NotFoundException;
import com.example.charityapi.repository.DonorRepository;
import com.example.charityapi.util.EmailValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonorService {

    private final DonorRepository donorRepository; // DIP

    public DonorService(DonorRepository donorRepository) {
        this.donorRepository = donorRepository;
    }

    public Donor create(Donor donor) {
        if (!EmailValidator.isValid(donor.getEmail())) {
            throw new IllegalArgumentException("Invalid email: " + donor.getEmail());
        }
        return donorRepository.save(donor);
    }

    public Donor get(Long id) {
        return donorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Donor not found: id=" + id));
    }

    public List<Donor> all() {
        return donorRepository.findAll();
    }

    public Donor update(Long id, Donor updated) {
        get(id);
        return donorRepository.update(id, updated);
    }

    public void delete(Long id) {
        get(id);
        donorRepository.deleteById(id);
    }
}
