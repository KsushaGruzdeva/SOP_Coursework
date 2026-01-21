package com.example.beauty_salon.services;

import demo.com.beauty_salon_api.dto.MasterRequest;
import demo.com.beauty_salon_api.dto.MasterResponse;

import java.util.List;

public interface MasterService {
    List<MasterResponse> findAll();
    MasterResponse findById(Long id);
    MasterResponse create(MasterRequest request);
    MasterResponse update(Long id, MasterRequest request);
    void delete(Long id);
}
