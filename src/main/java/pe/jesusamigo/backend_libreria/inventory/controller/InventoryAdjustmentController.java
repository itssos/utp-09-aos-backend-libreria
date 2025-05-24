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
import pe.jesusamigo.backend_libreria.inventory.dto.InventoryRequestDTO;
import pe.jesusamigo.backend_libreria.inventory.dto.StockMovementResponseDTO;
import pe.jesusamigo.backend_libreria.inventory.service.InventoryAdjustmentService;

@RestController
@RequestMapping("/api/inventory")
@Tag(name = "Inventario", description = "Operaciones para recarga, baja y consulta de stock de productos")
@RequiredArgsConstructor
public class InventoryAdjustmentController {

    private final InventoryAdjustmentService inventoryAdjustmentService;

    @Operation(
            summary = "Recargar stock de un producto",
            description = "Aumenta el stock disponible de un producto."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Stock recargado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StockMovementResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o producto no encontrado", content = @Content)
    })
    @PostMapping("/recharge")
    @PreAuthorize("hasAuthority('RECHARGE_STOCK')")
    public ResponseEntity<StockMovementResponseDTO> rechargeStock(
            @Parameter(description = "Datos de la recarga de inventario", required = true)
            @Valid @RequestBody InventoryRequestDTO request
    ) {
        StockMovementResponseDTO result = inventoryAdjustmentService.rechargeStock(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Operation(
            summary = "Dar de baja stock de un producto",
            description = "Reduce el stock disponible de un producto por merma, pérdida u otros motivos."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Stock dado de baja exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StockMovementResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos, producto no encontrado o stock insuficiente", content = @Content)
    })
    @PostMapping("/decrease")
    @PreAuthorize("hasAuthority('DECREASE_STOCK')")
    public ResponseEntity<StockMovementResponseDTO> decreaseStock(
            @Parameter(description = "Datos de la baja de inventario", required = true)
            @Valid @RequestBody InventoryRequestDTO request
    ) {
        StockMovementResponseDTO result = inventoryAdjustmentService.decreaseStock(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Operation(
            summary = "Consultar stock actual de un producto",
            description = "Devuelve el stock disponible de un producto por su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock consultado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(type = "integer"))),
            @ApiResponse(responseCode = "400", description = "Producto no encontrado", content = @Content)
    })
    @GetMapping("/product/{productId}/stock")
    @PreAuthorize("hasAuthority('GET_PRODUCT_STOCK')")
    public ResponseEntity<Integer> getStockByProductId(
            @Parameter(description = "ID del producto", example = "1", required = true)
            @PathVariable Integer productId
    ) {
        Integer stock = inventoryAdjustmentService.getStockByProductId(productId);
        return ResponseEntity.ok(stock);
    }
}
