package demo.com.beauty_salon_api.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public record AccommodationRequest(
    @NotBlank(message = "Название услуги не может быть пустым")
    String name,
    @NotNull(message = "Цена не может быть пустой")
    @Positive(message = "Цена должна быть положительной")
    Long price,
    @NotNull(message = "Длительность услуги не может быть пустой")
    LocalDateTime duration
) {}