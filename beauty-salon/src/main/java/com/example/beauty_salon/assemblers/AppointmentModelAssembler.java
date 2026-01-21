package com.example.beauty_salon.assemblers;

import com.example.beauty_salon.controllers.AppointmentController;
import demo.com.beauty_salon_api.dto.AppointmentResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AppointmentModelAssembler implements RepresentationModelAssembler<AppointmentResponse, EntityModel<AppointmentResponse>> {
    @Override
    public EntityModel<AppointmentResponse> toModel(AppointmentResponse appointment) {
        return EntityModel.of(appointment,
                linkTo(methodOn(AppointmentController.class).getAppointment(appointment.getId())).withSelfRel(),
                linkTo(methodOn(AppointmentController.class).getAllAppointments(null,0, 10)).withRel("collection")
        );
    }
}
