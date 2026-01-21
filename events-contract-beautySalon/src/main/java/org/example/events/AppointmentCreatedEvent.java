package org.example.events;

import java.io.Serializable;

public record AppointmentCreatedEvent(
        Long appointmentId,
        String startTime,
        String accommodationName,
        String masterName
) implements Serializable {}