package demo.com.beauty_salon_api.dto;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MasterRequest(
    @NotBlank(message = "ФИО не может быть пустым")
    String fullName,
    
    @NotBlank(message = "Телефон не может быть пустым")
    String phone,
    
    @Email(message = "Неверный формат email")
    String email,
    
    @NotNull(message = "Стаж не может быть пустым")
    Integer experienceYears,
    
    List<Long> accommodationsIds
) {}