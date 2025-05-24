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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.jesusamigo.backend_libreria.inventory.dto.StockMovementCreateDTO;
import pe.jesusamigo.backend_libreria.inventory.dto.StockMovementResponseDTO;
import pe.jesusamigo.backend_libreria.inventory.service.StockMovementService;

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
            summary = "Listar todos los movimientos de stock",
            description = "Obtiene el historial completo de movimientos de stock registrados."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de movimientos obtenida correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StockMovementResponseDTO.class)))
    })
    @GetMapping
    @PreAuthorize("hasAuthority('GET_STOCK_MOVEMENTS')")
    public ResponseEntity<List<StockMovementResponseDTO>> getAllStockMovements() {
        return ResponseEntity.ok(stockMovementService.findAll());
    }

    @Operation(
            summary = "Obtener movimientos de stock por producto",
            description = "Obtiene todos los movimientos de stock registrados para un producto específico."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movimientos del producto obtenidos correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StockMovementResponseDTO.class)))
    })
    @GetMapping("/product/{productId}")
    @PreAuthorize("hasAuthority('GET_STOCK_MOVEMENTS')")
    public ResponseEntity<List<StockMovementResponseDTO>> getStockMovementsByProduct(
            @Parameter(description = "ID del producto", example = "1", required = true)
            @PathVariable Integer productId) {
        return ResponseEntity.ok(stockMovementService.findByProductId(productId));
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
