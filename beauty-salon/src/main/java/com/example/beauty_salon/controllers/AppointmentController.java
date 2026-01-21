package com.example.beauty_salon.controllers;

import com.example.beauty_salon.assemblers.AppointmentModelAssembler;
import com.example.beauty_salon.services.AppointmentService;
import demo.com.beauty_salon_api.dto.AppointmentRequest;
import demo.com.beauty_salon_api.dto.AppointmentResponse;
import demo.com.beauty_salon_api.dto.PagedResponse;
import demo.com.beauty_salon_api.endpoints.AppointmentApi;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AppointmentController implements AppointmentApi {

    private final AppointmentService appointmentService;
    private final AppointmentModelAssembler appointmentModelAssembler;
    private final PagedResourcesAssembler<AppointmentResponse> pagedResourcesAssembler;

    public AppointmentController(AppointmentService appointmentService, AppointmentModelAssembler appointmentModelAssembler, PagedResourcesAssembler<AppointmentResponse> pagedResourcesAssembler) {
        this.appointmentService = appointmentService;
        this.appointmentModelAssembler = appointmentModelAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @Override
    public PagedModel<EntityModel<AppointmentResponse>> getAllAppointments(Long masterId, int page, int size) {
        PagedResponse<AppointmentResponse> pagedResponse = appointmentService.findAll(masterId, page, size);
        Page<AppointmentResponse> appointmentPage = new PageImpl<>(
                pagedResponse.content(),
                PageRequest.of(pagedResponse.pageNumber(), pagedResponse.pageSize()),
                pagedResponse.totalElements()
        );

        // PagedResourcesAssembler автоматически создаст PagedModel со всеми ссылками пагинации
        return pagedResourcesAssembler.toModel(appointmentPage, appointmentModelAssembler);
    }

    @Override
    public EntityModel<AppointmentResponse> getAppointment(Long id) {
        AppointmentResponse appointment = appointmentService.findById(id);
        return appointmentModelAssembler.toModel(appointment);
    }

    @Override
    public ResponseEntity<EntityModel<AppointmentResponse>> createAppointment(@Valid AppointmentRequest request) {
        AppointmentResponse createdAppointment = appointmentService.create(request);
        EntityModel<AppointmentResponse> entityModel = appointmentModelAssembler.toModel(createdAppointment);

        return ResponseEntity
                .created(entityModel.getRequiredLink("self").toUri())
                .body(entityModel);
    }

    @Override
    public EntityModel<AppointmentResponse> updateAppointment(Long id, @Valid AppointmentRequest request) {
        AppointmentResponse updatedBook = appointmentService.update(id, request);
        return appointmentModelAssembler.toModel(updatedBook);
    }

    @Override
    public void cancelAppointment(Long id) {
        appointmentService.delete(id);
    }
}
