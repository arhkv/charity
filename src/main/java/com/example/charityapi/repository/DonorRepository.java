package com.example.charityapi.repository;

import com.example.charityapi.entity.Donor;
import java.util.Optional;

public interface DonorRepository extends CrudRepository<Donor, Long> {
    Optional<Donor> findByEmail(String email);
}
