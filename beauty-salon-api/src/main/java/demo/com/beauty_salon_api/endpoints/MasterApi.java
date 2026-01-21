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

import demo.com.beauty_salon_api.dto.MasterRequest;
import demo.com.beauty_salon_api.dto.MasterResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "masters", description = "API для управления мастерами салона")
@RequestMapping("/api/masters")
public interface MasterApi {

    @Operation(summary = "Получить всех мастеров")
    @ApiResponse(responseCode = "200", description = "Список мастеров")
    @GetMapping
    CollectionModel<EntityModel<MasterResponse>> getAllMasters();

    @Operation(summary = "Получить мастера по ID")
    @ApiResponse(responseCode = "200", description = "Мастер найден")
    @ApiResponse(responseCode = "404", description = "Мастер не найден", content = @Content(schema = @Schema(implementation = StatusResponse.class)))
    @GetMapping("/{id}")
    EntityModel<MasterResponse> getMaster(@PathVariable Long id);

    @Operation(summary = "Создать нового мастера")
    @ApiResponse(responseCode = "201", description = "Мастер успешно создан")
    @ApiResponse(responseCode = "400", description = "Невалидный запрос", content = @Content(schema = @Schema(implementation = StatusResponse.class)))
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<EntityModel<MasterResponse>> createMaster(@Valid @RequestBody MasterRequest request);

    @Operation(summary = "Обновить мастера")
    @ApiResponse(responseCode = "200", description = "Мастер успешно обновлен")
    @ApiResponse(responseCode = "404", description = "Мастер не найден", content = @Content(schema = @Schema(implementation = StatusResponse.class)))
    @PutMapping("/{id}")
    EntityModel<MasterResponse> updateMaster(@PathVariable Long id, @Valid @RequestBody MasterRequest request);

    @Operation(summary = "Удалить мастера")
    @ApiResponse(responseCode = "204", description = "Мастер успешно удален")
    @ApiResponse(responseCode = "404", description = "Мастер не найден")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteMaster(@PathVariable Long id);
}
