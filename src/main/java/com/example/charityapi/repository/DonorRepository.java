package com.example.charityapi.repository;

import com.example.charityapi.entity.Donor;

import java.util.List;
import java.util.Optional;

public interface DonorRepository {
    Donor save(Donor donor);
    Optional<Donor> findById(Long id);
    List<Donor> findAll();
    Donor update(Long id, Donor donor);
    void deleteById(Long id);
}
