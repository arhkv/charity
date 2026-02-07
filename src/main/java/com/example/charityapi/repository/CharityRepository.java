package com.example.charityapi.repository;

import com.example.charityapi.entity.Charity;

import java.util.List;
import java.util.Optional;

public interface CharityRepository {
    Charity save(Charity charity);
    Optional<Charity> findById(Long id);
    List<Charity> findAll();
    Charity update(Long id, Charity charity);
    void deleteById(Long id);
}
