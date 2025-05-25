package pe.jesusamigo.backend_libreria.report.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.jesusamigo.backend_libreria.report.dto.ProductLowStockReportDTO;
import pe.jesusamigo.backend_libreria.report.dto.ProductSalesReportDTO;
import pe.jesusamigo.backend_libreria.report.dto.SaleReportDTO;
import pe.jesusamigo.backend_libreria.report.service.ProductReportService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reports/products")
@RequiredArgsConstructor
@Tag(name = "Product Reports", description = "Endpoints para reportes de productos")
public class ProductReportController {

    private final ProductReportService productReportService;

    @GetMapping("/top-sold")
    @PreAuthorize("hasAuthority('REPORTS_VIEW')")
    @Operation(
            summary = "Obtener productos más vendidos",
            description = "Retorna la lista de productos más vendidos ordenados por cantidad. Puedes filtrar por fecha de venta (startDate, endDate) y la cantidad de productos (limit, obligatorio)."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reporte generado correctamente"),
            @ApiResponse(responseCode = "403", description = "No autorizado")
    })
    public ResponseEntity<List<ProductSalesReportDTO>> getTopSellingProducts(
            @RequestParam(name = "limit") int limit,
            @RequestParam(name = "startDate", required = false)
            @Parameter(description = "Fecha de inicio (yyyy-MM-ddTHH:mm:ss)") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(name = "endDate", required = false)
            @Parameter(description = "Fecha de fin (yyyy-MM-ddTHH:mm:ss)") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    ) {
        List<ProductSalesReportDTO> result = productReportService.getTopSellingProducts(limit, startDate, endDate);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/low-stock")
    @PreAuthorize("hasAuthority('REPORTS_VIEW')")
    @Operation(
            summary = "Obtener productos con stock bajo",
            description = "Devuelve la lista de productos cuyo stock es menor o igual al umbral especificado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reporte generado correctamente"),
            @ApiResponse(responseCode = "403", description = "No autorizado")
    })
    public ResponseEntity<List<ProductLowStockReportDTO>> getProductsWithLowStock(
            @RequestParam(name = "threshold", defaultValue = "5")
            Integer threshold
    ) {
        List<ProductLowStockReportDTO> result = productReportService.getProductsWithLowStock(threshold);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/sales")
    @PreAuthorize("hasAuthority('REPORTS_VIEW')")
    @Operation(
            summary = "Reporte de ventas filtrado",
            description = "Filtra ventas por rango de fechas, producto y usuario. Todos los filtros son opcionales, excepto el límite (limit) que es obligatorio."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reporte generado correctamente"),
            @ApiResponse(responseCode = "403", description = "No autorizado")
    })
    public ResponseEntity<List<SaleReportDTO>> getSalesReport(
            @RequestParam(name = "limit") int limit,
            @RequestParam(name = "startDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(name = "endDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(name = "productId", required = false) Integer productId,
            @RequestParam(name = "userId", required = false) Integer userId
    ) {
        List<SaleReportDTO> result = productReportService.getSalesReport(
                startDate, endDate, productId, userId, limit
        );
        return ResponseEntity.ok(result);
    }

}
