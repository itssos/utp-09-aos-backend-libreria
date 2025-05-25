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
import org.springframework.data.domain.Page;
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

    @Operation(
            summary = "Consultar historial de ventas filtrado, paginado y ordenado",
            description = "Filtra ventas por usuario (opcional), por rango de fechas, y permite paginación y orden por fecha."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ventas obtenidas correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SaleResponseDTO.class)))
    })
    @GetMapping
    @PreAuthorize("hasAuthority('GET_SALES')")
    public ResponseEntity<Page<SaleResponseDTO>> getSales(
            @Parameter(description = "ID del usuario vendedor") @RequestParam(required = false) Integer userId,
            @Parameter(description = "Fecha inicial (yyyy-MM-dd'T'HH:mm:ss)") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "Fecha final (yyyy-MM-dd'T'HH:mm:ss)") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @Parameter(description = "Ordenar por fecha (asc o desc)", example = "desc") @RequestParam(required = false, defaultValue = "desc") String sort,
            @Parameter(description = "Número de página (inicia en 0)", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamaño de página", example = "20") @RequestParam(defaultValue = "20") int size
    ) {
        Page<SaleResponseDTO> sales = saleService.findAllFiltered(
                userId, startDate, endDate, sort, page, size
        );
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
}
