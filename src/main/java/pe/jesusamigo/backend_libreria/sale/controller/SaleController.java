package pe.jesusamigo.backend_libreria.sale.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.jesusamigo.backend_libreria.sale.dto.SaleCreateDTO;
import pe.jesusamigo.backend_libreria.sale.dto.SaleResponseDTO;
import pe.jesusamigo.backend_libreria.sale.service.SaleService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/sales")
@Tag(name = "Ventas", description = "Gestión de ventas y consulta de historial")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;

    @Operation(
            summary = "Registrar una nueva venta",
            description = "Registra una venta con su detalle e impacta en el stock de los productos vendidos."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Venta registrada exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SaleResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos, usuario o productos no encontrados, o stock insuficiente", content = @Content)
    })
    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_SALE')")
    public ResponseEntity<SaleResponseDTO> registerSale(
            @Parameter(description = "Datos de la venta a registrar", required = true)
            @Valid @RequestBody SaleCreateDTO saleCreateDTO
    ) {
        SaleResponseDTO result = saleService.registerSale(saleCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('GET_SALES')")
    @Operation(
            summary = "Consultar historial de ventas (opcionalmente por rango de fechas)",
            description = "Obtiene todas las ventas o solo las del rango [startDate, endDate] si se especifican los parámetros."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ventas obtenidas correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SaleResponseDTO.class)))
    })
    public ResponseEntity<List<SaleResponseDTO>> getSales(
            @Parameter(description = "Fecha inicial (formato: yyyy-MM-dd'T'HH:mm:ss)", example = "2024-05-20T00:00:00")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "Fecha final (formato: yyyy-MM-dd'T'HH:mm:ss)", example = "2024-05-23T23:59:59")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    ) {
        List<SaleResponseDTO> sales;
        if (startDate != null && endDate != null) {
            sales = saleService.findBySaleDateBetween(startDate, endDate);
        } else {
            sales = saleService.findAll();
        }
        return ResponseEntity.ok(sales);
    }

    @Operation(
            summary = "Obtener venta por ID",
            description = "Recupera los datos de una venta específica usando su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venta encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SaleResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Venta no encontrada", content = @Content)
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('GET_SALE')")
    public ResponseEntity<SaleResponseDTO> getSaleById(
            @Parameter(description = "ID de la venta a consultar", example = "1", required = true)
            @PathVariable Integer id
    ) {
        return saleService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Consultar historial de ventas de un usuario",
            description = "Obtiene todas las ventas realizadas por un usuario específico."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Historial obtenido correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SaleResponseDTO.class)))
    })
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAuthority('GET_SALES')")
    public ResponseEntity<List<SaleResponseDTO>> getSalesByUserId(
            @Parameter(description = "ID del usuario vendedor", example = "7", required = true)
            @PathVariable Integer userId
    ) {
        return ResponseEntity.ok(saleService.findByUserId(userId));
    }
}
