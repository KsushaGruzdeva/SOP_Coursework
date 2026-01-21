package com.example.beauty_salon.controllers;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class RootController {

    @GetMapping("/api")
    public RepresentationModel<?> getRoot() {
        RepresentationModel<?> rootModel = new RepresentationModel<>();
        rootModel.add(
                linkTo(methodOn(AccommodationController.class).getAllAccommodations()).withRel("accommodations"),
                linkTo(methodOn(MasterController.class).getAllMasters()).withRel("masters"),
                linkTo(methodOn(AppointmentController.class).getAllAppointments(null,0, 10)).withRel("appointments")
        );
        return rootModel;
    }

    @GetMapping("/swagger")
    public RepresentationModel<?> getSwagger() {
        RepresentationModel<?> rootModel = new RepresentationModel<>();
        rootModel.add(
                Link.of("/swagger-ui.html").withRel("documentation")
        );
        return rootModel;
    }
}

