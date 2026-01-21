package com.example.beauty_salon.controllers;

import com.example.beauty_salon.assemblers.MasterModelAssembler;
import com.example.beauty_salon.services.MasterService;
import demo.com.beauty_salon_api.dto.MasterRequest;
import demo.com.beauty_salon_api.dto.MasterResponse;
import demo.com.beauty_salon_api.endpoints.MasterApi;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MasterController implements MasterApi {

    private final MasterService masterService;
    private final MasterModelAssembler masterModelAssembler;

    public MasterController(MasterService masterService, MasterModelAssembler masterModelAssembler) {
        this.masterService = masterService;
        this.masterModelAssembler = masterModelAssembler;
    }

    @Override
    public CollectionModel<EntityModel<MasterResponse>> getAllMasters() {
        List<MasterResponse> masters = masterService.findAll();
        return masterModelAssembler.toCollectionModel(masters);
    }

    @Override
    public EntityModel<MasterResponse> getMaster(Long id) {
        MasterResponse master = masterService.findById(id);
        return masterModelAssembler.toModel(master);
    }

    @Override
    public ResponseEntity<EntityModel<MasterResponse>> createMaster(@Valid MasterRequest request) {
        MasterResponse createdAuthor = masterService.create(request);
        EntityModel<MasterResponse> entityModel = masterModelAssembler.toModel(createdAuthor);

        return ResponseEntity
                .created(entityModel.getRequiredLink("self").toUri())
                .body(entityModel);
    }

    @Override
    public EntityModel<MasterResponse> updateMaster(Long id, @Valid MasterRequest request) {
        MasterResponse updatedAuthor = masterService.update(id, request);
        return masterModelAssembler.toModel(updatedAuthor);
    }

    @Override
    public void deleteMaster(Long id) {
        masterService.delete(id);
    }
}
