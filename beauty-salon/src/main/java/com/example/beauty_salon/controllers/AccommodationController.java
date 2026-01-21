package com.example.beauty_salon.controllers;

import com.example.beauty_salon.assemblers.AccommodationModelAssembler;
import com.example.beauty_salon.services.AccommodationService;
import demo.com.beauty_salon_api.dto.AccommodationRequest;
import demo.com.beauty_salon_api.dto.AccommodationResponse;
import demo.com.beauty_salon_api.endpoints.AccommodationApi;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccommodationController implements AccommodationApi{

    private final AccommodationService accommodationService;
    private final AccommodationModelAssembler accommodationModelAssembler;

    public AccommodationController(AccommodationService accommodationService, AccommodationModelAssembler accommodationModelAssembler) {
        this.accommodationService = accommodationService;
        this.accommodationModelAssembler = accommodationModelAssembler;
    }

    @Override
    public CollectionModel<EntityModel<AccommodationResponse>> getAllAccommodations() {
        List<AccommodationResponse> accommodations = accommodationService.findAll();
        return accommodationModelAssembler.toCollectionModel(accommodations);
    }

    @Override
    public EntityModel<AccommodationResponse> getAccommodation(Long id) {
        AccommodationResponse accommodationResponse = accommodationService.findById(id);
        return accommodationModelAssembler.toModel(accommodationResponse);
    }

    @Override
    public ResponseEntity<EntityModel<AccommodationResponse>> createAccommodation(@Valid AccommodationRequest request) {
        AccommodationResponse createdAccommodation = accommodationService.create(request);
        EntityModel<AccommodationResponse> entityModel = accommodationModelAssembler.toModel(createdAccommodation);

        return ResponseEntity
                .created(entityModel.getRequiredLink("self").toUri())
                .body(entityModel);
    }

    @Override
    public EntityModel<AccommodationResponse> updateAccommodation(Long id, @Valid AccommodationRequest request) {
        AccommodationResponse updatedAccommodation = accommodationService.update(id, request);
        return accommodationModelAssembler.toModel(updatedAccommodation);
    }

    @Override
    public void deleteAccommodation(Long id) {
        accommodationService.delete(id);
    }
}
