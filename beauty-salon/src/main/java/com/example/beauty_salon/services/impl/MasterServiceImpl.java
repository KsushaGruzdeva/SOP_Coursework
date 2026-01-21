package com.example.beauty_salon.services.impl;

import com.example.beauty_salon.services.AccommodationService;
import com.example.beauty_salon.services.MasterService;
import com.example.beauty_salon.storage.InMemoryStorage;
import demo.com.beauty_salon_api.dto.AccommodationResponse;
import demo.com.beauty_salon_api.dto.MasterRequest;
import demo.com.beauty_salon_api.dto.MasterResponse;
import demo.com.beauty_salon_api.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MasterServiceImpl implements MasterService {
    private final InMemoryStorage storage;

    public MasterServiceImpl(InMemoryStorage storage) {
        this.storage = storage;
    }

    @Override
    public List<MasterResponse> findAll() {
        return storage.masters.values().stream().toList();
    }

    @Override
    public MasterResponse findById(Long id) {
        return Optional.ofNullable(storage.masters.get(id))
                .orElseThrow(() -> new ResourceNotFoundException("Master", id));
    }

    @Override
    public MasterResponse create(MasterRequest request) {
        long id = storage.masterSequence.incrementAndGet();
        List<AccommodationResponse> accommodations = new ArrayList<>();
        for (Long accommodationId : request.accommodationsIds()) {
            AccommodationResponse accommodation = storage.accommodations.get(accommodationId);
            if (accommodation != null) {
                accommodations.add(accommodation);
            }
            else {
                throw new ResourceNotFoundException("Accommodation", accommodationId);

            }
        }

        MasterResponse master = new MasterResponse(id, request.fullName(), request.phone(), request.email(), request.experienceYears(), accommodations);
        storage.masters.put(id, master);
        return master;
    }

    @Override
    public MasterResponse update(Long id, MasterRequest request) {
        findById(id);
        List<AccommodationResponse> accommodations = new ArrayList<>();
        for (Long accommodationId : request.accommodationsIds()) {
            AccommodationResponse accommodation = storage.accommodations.get(accommodationId);
            if (accommodation != null) {
                accommodations.add(accommodation);
            }
            else {
                throw new ResourceNotFoundException("Accommodation", accommodationId);

            }
        }

        MasterResponse master = new MasterResponse(id, request.fullName(), request.phone(), request.email(), request.experienceYears(), accommodations);
        storage.masters.put(id, master);
        return master;
    }

    @Override
    public void delete(Long id) {
        findById(id); // Проверяем, что мастер существует

        // Удаляем самого мастера
        storage.masters.remove(id);
    }
}
