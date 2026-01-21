package demo.com.beauty_salon_api.endpoints;

import java.time.LocalDateTime;
import java.util.List;

import demo.com.beauty_salon_api.dto.StatusResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import demo.com.beauty_salon_api.dto.AppointmentRequest;
import demo.com.beauty_salon_api.dto.AppointmentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "appointments", description = "API для управления записями клиентов")
@RequestMapping("/api/appointments")
public interface AppointmentApi {

    @Operation(summary = "Получить все записи с фильтрацией и пагинацией")
    @ApiResponse(responseCode = "200", description = "Список записей")
    @GetMapping
    PagedModel<EntityModel<AppointmentResponse>> getAllAppointments(
            @Parameter(description = "Фильтр по ID клиента") @RequestParam(required = false) Long clientId,
            @Parameter(description = "Номер страницы (0..N)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Размер страницы") @RequestParam(defaultValue = "10") int size
    );

    @Operation(summary = "Получить запись по ID")
    @ApiResponse(responseCode = "200", description = "Запись найдена")
    @ApiResponse(responseCode = "404", description = "Запись не найдена", content = @Content(schema = @Schema(implementation = StatusResponse.class)))
    @GetMapping("/{id}")
    EntityModel<AppointmentResponse> getAppointment(@PathVariable Long id);

    @Operation(summary = "Создать новую запись")
    @ApiResponse(responseCode = "201", description = "Запись успешно создана")
    @ApiResponse(responseCode = "400", description = "Невалидный запрос", content = @Content(schema = @Schema(implementation = StatusResponse.class)))
    @ApiResponse(responseCode = "409", description = "Время занято", content = @Content(schema = @Schema(implementation = StatusResponse.class)))
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<EntityModel<AppointmentResponse>> createAppointment(@Valid @RequestBody AppointmentRequest request);

    @Operation(summary = "Обновить запись")
    @ApiResponse(responseCode = "200", description = "Запись успешно обновлена")
    @ApiResponse(responseCode = "404", description = "Запись не найдена", content = @Content(schema = @Schema(implementation = StatusResponse.class)))
    @ApiResponse(responseCode = "409", description = "Время занято", content = @Content(schema = @Schema(implementation = StatusResponse.class)))
    @PutMapping("/{id}")
    EntityModel<AppointmentResponse> updateAppointment(@PathVariable Long id, @Valid @RequestBody AppointmentRequest request);

    @Operation(summary = "Отменить запись")
    @ApiResponse(responseCode = "204", description = "Запись успешно отменена")
    @ApiResponse(responseCode = "404", description = "Запись не найдена")
    @PatchMapping("/{id}/cancel")
    void cancelAppointment(@PathVariable Long id);
}
