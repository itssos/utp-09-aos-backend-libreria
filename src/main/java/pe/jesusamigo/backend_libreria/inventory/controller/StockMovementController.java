package pe.jesusamigo.backend_libreria.inventory.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.jesusamigo.backend_libreria.inventory.dto.StockMovementCreateDTO;
import pe.jesusamigo.backend_libreria.inventory.dto.StockMovementResponseDTO;
import pe.jesusamigo.backend_libreria.inventory.entity.StockMovement;
import pe.jesusamigo.backend_libreria.inventory.service.StockMovementService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/stock-movements")
@Tag(name = "Movimientos de Stock", description = "Gestión del historial y operaciones de inventario")
@RequiredArgsConstructor
public class StockMovementController {

    private final StockMovementService stockMovementService;

    @Operation(
            summary = "Registrar un nuevo movimiento de stock",
            description = "Registra una entrada, salida o ajuste de stock para un producto."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Movimiento registrado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StockMovementResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o stock insuficiente", content = @Content)
    })
    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_STOCK_MOVEMENT')")
    public ResponseEntity<?> createStockMovement(
            @Parameter(description = "Datos del movimiento de stock a registrar", required = true)
            @Valid @RequestBody StockMovementCreateDTO createDTO) {
        StockMovementResponseDTO created = stockMovementService.create(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(
            summary = "Actualizar un movimiento de stock existente",
            description = "Actualiza los datos de un movimiento de stock existente y su efecto sobre el producto."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movimiento actualizado correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StockMovementResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o stock insuficiente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Movimiento no encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_STOCK_MOVEMENT')")
    public ResponseEntity<?> updateStockMovement(
            @Parameter(description = "ID del movimiento de stock a actualizar", example = "1", required = true)
            @PathVariable Integer id,
            @Parameter(description = "Datos actualizados del movimiento de stock", required = true)
            @Valid @RequestBody StockMovementCreateDTO updateDTO) {
        return stockMovementService.update(id, updateDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(
            summary = "Listar movimientos de stock con filtros, paginación y orden",
            description = "Filtra movimientos por producto, tipo (IN/OUT), rango de fechas. Permite paginar y ordenar por fecha."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Página de movimientos obtenida correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StockMovementResponseDTO.class)))
    })
    @GetMapping
    @PreAuthorize("hasAuthority('GET_STOCK_MOVEMENTS')")
    public ResponseEntity<Page<StockMovementResponseDTO>> getStockMovements(
            @Parameter(description = "ID del producto") @RequestParam(required = false) Integer productId,
            @Parameter(description = "Tipo de movimiento (IN/OUT)") @RequestParam(required = false) StockMovement.MovementType movementType,
            @Parameter(description = "Fecha inicial (yyyy-MM-dd'T'HH:mm:ss)") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDate,
            @Parameter(description = "Fecha final (yyyy-MM-dd'T'HH:mm:ss)") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toDate,
            @Parameter(description = "Ordenar por fecha (asc o desc)", example = "desc") @RequestParam(required = false, defaultValue = "desc") String sort,
            @Parameter(description = "Número de página (inicia en 0)", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamaño de página", example = "20") @RequestParam(defaultValue = "20") int size
    ) {
        Page<StockMovementResponseDTO> movimientos = stockMovementService.findAllFiltered(
                productId, movementType, fromDate, toDate, sort, page, size
        );
        return ResponseEntity.ok(movimientos);
    }


    @Operation(
            summary = "Obtener un movimiento de stock por ID",
            description = "Recupera la información de un movimiento de stock específico usando su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movimiento encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StockMovementResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Movimiento no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('GET_STOCK_MOVEMENT')")
    public ResponseEntity<StockMovementResponseDTO> getStockMovementById(
            @Parameter(description = "ID del movimiento de stock", example = "1", required = true)
            @PathVariable Integer id) {
        return stockMovementService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
