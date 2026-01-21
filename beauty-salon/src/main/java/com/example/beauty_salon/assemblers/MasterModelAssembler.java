package com.example.beauty_salon.assemblers;

import com.example.beauty_salon.controllers.MasterController;
import demo.com.beauty_salon_api.dto.MasterResponse;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class MasterModelAssembler implements RepresentationModelAssembler<MasterResponse, EntityModel<MasterResponse>> {
    @Override
    public EntityModel<MasterResponse> toModel(MasterResponse master) {
        return EntityModel.of(master,
                linkTo(methodOn(MasterController.class).getMaster(master.getId())).withSelfRel(),
                linkTo(methodOn(MasterController.class).getAllMasters()).withRel("collection")
        );
    }

    @Override
    public CollectionModel<EntityModel<MasterResponse>> toCollectionModel(Iterable<? extends MasterResponse> entities) {     return RepresentationModelAssembler.super.toCollectionModel(entities)
               .add(linkTo(methodOn(MasterController.class).getAllMasters()).withSelfRel());
    }
}
