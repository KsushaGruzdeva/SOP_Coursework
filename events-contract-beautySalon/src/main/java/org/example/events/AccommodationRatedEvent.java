package org.example.events;

import java.io.Serializable;

public record AccommodationRatedEvent(Long accommodationId, Integer score, String verdict) implements Serializable {}