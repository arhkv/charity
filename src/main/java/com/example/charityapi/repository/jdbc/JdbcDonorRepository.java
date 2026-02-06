package com.example.charityapi.repository;

import com.example.charityapi.entity.Donation;

import java.util.List;

public interface DonationRepository extends CrudRepository<Donation, Long> {
    List<Donation> findByDonorId(Long donorId);
    List<Donation> findByCharityId(Long charityId);
    List<Donation> findByAmountGreaterThan(long minAmount);
}
