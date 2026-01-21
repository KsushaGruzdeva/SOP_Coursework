package com.example.beauty_salon.services;

import demo.com.beauty_salon_api.dto.AccommodationRequest;
import demo.com.beauty_salon_api.dto.AccommodationResponse;

import java.util.List;

public interface AccommodationService {
    List<AccommodationResponse> findAll();
    AccommodationResponse findById(Long id);
    AccommodationResponse create(AccommodationRequest request);
    AccommodationResponse update(Long id, AccommodationRequest request);
    void delete(Long id);
}
