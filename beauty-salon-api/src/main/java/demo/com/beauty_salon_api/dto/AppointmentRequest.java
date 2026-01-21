package demo.com.beauty_salon_api.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AppointmentRequest(
    @NotNull(message = "Услуга не может быть пустой")
    Long accommodationId,
    
    @NotNull(message = "Мастер не может быть пустым")
    Long masterId,
    
    @NotNull(message = "Время начала не может быть пустым")
    @Future(message = "Время начала должно быть в будущем")
    LocalDateTime startTime
) {}