package com.example.beauty_salon.graphql;

import com.example.beauty_salon.services.AppointmentService;
import com.netflix.graphql.dgs.*;
import demo.com.beauty_salon_api.dto.AppointmentRequest;
import demo.com.beauty_salon_api.dto.AppointmentResponse;
import demo.com.beauty_salon_api.dto.PagedResponse;

import java.time.LocalDateTime;
import java.util.Map;

@DgsComponent
public class AppointmentDataFetcher {

    private final AppointmentService appointmentService;

    public AppointmentDataFetcher(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @DgsQuery
    public AppointmentResponse appointmentById(@InputArgument Long id) {
        return appointmentService.findById(id);
    }

    @DgsQuery
    public PagedResponse<AppointmentResponse> appointments(@InputArgument Long masterId, @InputArgument int page, @InputArgument int size) {
        return appointmentService.findAll(masterId, page, size);
    }

    @DgsMutation
    public AppointmentResponse createAppointment(@InputArgument("input") Map<String, Object> input) {
        AppointmentRequest request = new AppointmentRequest(
                Long.parseLong(input.get("accommodationId").toString()),
                Long.parseLong(input.get("masterId").toString()),
                LocalDateTime.parse(input.get("startTime").toString())
        );
        return appointmentService.create(request);
    }

    @DgsMutation
    public AppointmentResponse updateAppointment(@InputArgument Long id, @InputArgument("input") Map<String, String> input) {
        AppointmentRequest request = new AppointmentRequest(
                Long.parseLong(input.get("accommodationId").toString()),
                Long.parseLong(input.get("masterId").toString()),
                LocalDateTime.parse(input.get("startTime").toString())
                );
        return appointmentService.update(id, request);
    }

    @DgsMutation
    public Long deleteAppointment(@InputArgument Long id) {
        appointmentService.delete(id);
        return id;
    }
}
