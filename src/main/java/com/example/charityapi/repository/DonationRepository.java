package com.example.charityapi.repository;

import com.example.charityapi.entity.Donation;

import java.util.List;
import java.util.Optional;

public interface DonationRepository {
    Donation save(Donation donation);
    Optional<Donation> findById(Long id);
    List<Donation> findAll();
    List<Donation> findByDonorId(Long donorId);
    List<Donation> findByCharityId(Long charityId);
    Donation update(Long id, Donation donation);
    void deleteById(Long id);
}
