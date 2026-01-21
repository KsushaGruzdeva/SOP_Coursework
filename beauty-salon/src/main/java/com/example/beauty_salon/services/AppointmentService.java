package com.example.beauty_salon.services;

import demo.com.beauty_salon_api.dto.AppointmentRequest;
import demo.com.beauty_salon_api.dto.AppointmentResponse;
import demo.com.beauty_salon_api.dto.PagedResponse;

import java.util.List;

public interface AppointmentService {
    PagedResponse<AppointmentResponse> findAll(Long masterId, int page, int size);
    AppointmentResponse findById(Long id);
    AppointmentResponse create(AppointmentRequest request);
    AppointmentResponse update(Long id, AppointmentRequest request);
    void delete(Long id);
}
