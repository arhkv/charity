package com.example.charityapi.service;

import com.example.charityapi.entity.Donation;
import com.example.charityapi.exception.InvalidDonationAmountException;
import com.example.charityapi.exception.NotFoundException;
import com.example.charityapi.repository.DonationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DonationService {

    private final DonationRepository donationRepository;
    private final DonorService donorService;
    private final CharityService charityService;

    public DonationService(DonationRepository donationRepository, DonorService donorService, CharityService charityService) {
        this.donationRepository = donationRepository;
        this.donorService = donorService;
        this.charityService = charityService;
    }

    public Donation create(Donation donation) {
        if (donation.getAmount() <= 0) {
            throw new InvalidDonationAmountException("Amount must be > 0");
        }

        // donatedAt по умолчанию
        if (donation.getDonatedAt() == null) {
            donation.setDonatedAt(LocalDateTime.now());
        }

        // Проверяем существование donor и charity (иначе 404)
        donorService.get(donation.getDonorId());
        charityService.get(donation.getCharityId());

        return donationRepository.save(donation);
    }

    public Donation get(Long id) {
        return donationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Donation not found: id=" + id));
    }

    public List<Donation> all() {
        return donationRepository.findAll();
    }

    public List<Donation> byDonor(Long donorId) {
        donorService.get(donorId);
        return donationRepository.findByDonorId(donorId);
    }

    public List<Donation> byCharity(Long charityId) {
        charityService.get(charityId);
        return donationRepository.findByCharityId(charityId);
    }

    // Data Pool + Lambda: фильтр/сортировка в памяти
    public List<Donation> topAbove(long minAmount) {
        return donationRepository.findAll().stream()
                .filter(d -> d.getAmount() > minAmount)   // Lambda
                .sorted((a, b) -> Long.compare(b.getAmount(), a.getAmount()))
                .toList();
    }

    public Donation update(Long id, Donation d) {
        Donation existing = get(id);

        // если amount не задан или <= 0 — берём старый (или можно кидать exception)
        if (d.getAmount() <= 0) {
            d.setAmount(existing.getAmount());
        }

        // если donatedAt не передали — оставляем старую (или ставим now)
        if (d.getDonatedAt() == null) {
            d.setDonatedAt(existing.getDonatedAt() != null ? existing.getDonatedAt() : LocalDateTime.now());
        }

        // если donor/charity не передали — оставляем старые
        if (d.getDonorId() == null) {
            d.setDonorId(existing.getDonorId());
        }
        if (d.getCharityId() == null) {
            d.setCharityId(existing.getCharityId());
        }

        // проверка существования после подстановки
        donorService.get(d.getDonorId());
        charityService.get(d.getCharityId());

        return donationRepository.update(id, d);
    }

    public void delete(Long id) {
        get(id);
        donationRepository.deleteById(id);
    }
}
