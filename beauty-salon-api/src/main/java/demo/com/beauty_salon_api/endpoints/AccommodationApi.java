package demo.com.beauty_salon_api.endpoints;

import java.util.List;

import demo.com.beauty_salon_api.dto.StatusResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import demo.com.beauty_salon_api.dto.AccommodationRequest;
import demo.com.beauty_salon_api.dto.AccommodationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name="accommodations", description="API для управления услугами салона")
@RequestMapping("api/accommodations")
public interface AccommodationApi {
    
    @Operation(summary = "Получить все услуги")
    @ApiResponse(responseCode = "200", description = "Список услуг")
    @GetMapping
    CollectionModel<EntityModel<AccommodationResponse>> getAllAccommodations();

    @Operation(summary = "Получить услугу по ID")
    @ApiResponse(responseCode = "200", description = "Услуга найдена")
    @ApiResponse(responseCode = "404", description = "Услуга не найдена", content = @Content(schema = @Schema(implementation = StatusResponse.class)))
    @GetMapping("/{id}")
    EntityModel<AccommodationResponse> getAccommodation(@PathVariable Long id);

    @Operation(summary = "Создать новую услугу")
    @ApiResponse(responseCode = "201", description = "Услуга успешно создана")
    @ApiResponse(responseCode = "400", description = "Невалидный запрос", content = @Content(schema = @Schema(implementation = StatusResponse.class)))
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<EntityModel<AccommodationResponse>> createAccommodation(@Valid @RequestBody AccommodationRequest request);

    @Operation(summary = "Обновить услугу")
    @ApiResponse(responseCode = "200", description = "Услуга успешно обновлена")
    @ApiResponse(responseCode = "404", description = "Услуга не найдена", content = @Content(schema = @Schema(implementation = StatusResponse.class)))
    @PutMapping("/{id}")
    EntityModel<AccommodationResponse> updateAccommodation(@PathVariable Long id, @Valid @RequestBody AccommodationRequest request);

    @Operation(summary = "Удалить услугу")
    @ApiResponse(responseCode = "204", description = "Услуга успешно удалена")
    @ApiResponse(responseCode = "404", description = "Услуга не найдена")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteAccommodation(@PathVariable Long id);
}
