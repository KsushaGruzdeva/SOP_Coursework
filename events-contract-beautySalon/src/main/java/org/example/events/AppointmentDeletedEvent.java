package org.example.events;

import java.io.Serializable;

public record AppointmentDeletedEvent (
        Long appointmentId
) implements Serializable {}