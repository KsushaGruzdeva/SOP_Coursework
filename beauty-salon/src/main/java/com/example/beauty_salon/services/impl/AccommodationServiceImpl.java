package com.example.beauty_salon.services.impl;

import com.example.beauty_salon.services.AccommodationService;
import com.example.beauty_salon.services.MasterService;
import com.example.beauty_salon.storage.InMemoryStorage;
import demo.com.beauty_salon_api.dto.AccommodationRequest;
import demo.com.beauty_salon_api.dto.AccommodationResponse;
import demo.com.beauty_salon_api.dto.MasterResponse;
import demo.com.beauty_salon_api.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Lazy;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccommodationServiceImpl implements AccommodationService {
    private final InMemoryStorage storage;
    private final MasterService masterService;

    public AccommodationServiceImpl(InMemoryStorage storage, @Lazy MasterService masterService) {
        this.storage = storage;
        this.masterService = masterService;
    }

    @Override
    public List<AccommodationResponse> findAll() {
        return storage.accommodations.values().stream().toList();
    }

    @Override
    public AccommodationResponse findById(Long id) {
        return Optional.ofNullable(storage.accommodations.get(id))
                .orElseThrow(() -> new ResourceNotFoundException("Accommodation", id));
    }

    @Override
    public AccommodationResponse create(AccommodationRequest request) {
        long id = storage.accommodationSequence.incrementAndGet();
        AccommodationResponse accommodation = new AccommodationResponse(id, request.name(), request.price(), request.duration());
        storage.accommodations.put(id, accommodation);
        return accommodation;
    }

    @Override
    public AccommodationResponse update(Long id, AccommodationRequest request) {
        findById(id);
        AccommodationResponse updatedAuthor = new AccommodationResponse(id, request.name(), request.price(), request.duration());
        storage.accommodations.put(id, updatedAuthor);
        return updatedAuthor;
    }

    @Override
    public void delete(Long id) {
        AccommodationResponse accommodation = findById(id);

        for (MasterResponse master : storage.masters.values()) {
            List<AccommodationResponse> updatedAccommodations = master.getAccommodations().stream()
                    .filter(acc -> !acc.getId().equals(id))
                    .collect(Collectors.toList());

            MasterResponse updatedMaster = new MasterResponse(
                    master.getId(),
                    master.getFullName(),
                    master.getPhone(),
                    master.getEmail(),
                    master.getExperienceYears(),
                    updatedAccommodations
            );

            storage.masters.put(master.getId(), updatedMaster);
        }

        storage.accommodations.remove(id);
    }
}
