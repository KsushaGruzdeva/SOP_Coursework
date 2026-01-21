package com.example.beauty_salon.assemblers;

import com.example.beauty_salon.controllers.AccommodationController;
import demo.com.beauty_salon_api.dto.AccommodationResponse;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AccommodationModelAssembler implements RepresentationModelAssembler<AccommodationResponse, EntityModel<AccommodationResponse>> {
    @Override
    public EntityModel<AccommodationResponse> toModel(AccommodationResponse accommodation) {
        return EntityModel.of(accommodation,
                linkTo(methodOn(AccommodationController.class).getAccommodation(accommodation.getId())).withSelfRel(),
                linkTo(methodOn(AccommodationController.class).getAllAccommodations()).withRel("collection")
        );
    }

    @Override
    public CollectionModel<EntityModel<AccommodationResponse>> toCollectionModel(Iterable<? extends AccommodationResponse> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities)
                .add(linkTo(methodOn(AccommodationController.class).getAllAccommodations()).withSelfRel());
    }
}
