package com.example.charityapi.service;

import com.example.charityapi.entity.Charity;
import com.example.charityapi.exception.NotFoundException;
import com.example.charityapi.repository.CharityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharityService {

    private final CharityRepository charityRepository;

    public CharityService(CharityRepository charityRepository) {
        this.charityRepository = charityRepository;
    }

    public Charity create(Charity charity) {
        return charityRepository.save(charity);
    }

    public Charity get(Long id) {
        return charityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Charity not found: id=" + id));
    }

    public List<Charity> all() {
        return charityRepository.findAll();
    }

    public Charity update(Long id, Charity updated) {
        get(id);
        return charityRepository.update(id, updated);
    }

    public void delete(Long id) {
        get(id);
        charityRepository.deleteById(id);
    }
}
