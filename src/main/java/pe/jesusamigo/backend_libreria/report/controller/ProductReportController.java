package pe.jesusamigo.backend_libreria.report.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.jesusamigo.backend_libreria.report.dto.ProductSalesReportDTO;
import pe.jesusamigo.backend_libreria.report.service.ProductReportService;

import java.util.List;

@RestController
@RequestMapping("/api/reports/products")
@RequiredArgsConstructor
@Tag(name = "Product Reports", description = "Endpoints para reportes de productos")
public class ProductReportController {

    private final ProductReportService productReportService;

    @GetMapping("/top-sold")
    @PreAuthorize("hasAuthority('REPORTS_VIEW')")
    @Operation(summary = "Obtener productos más vendidos", description = "Retorna la lista de productos más vendidos ordenados por cantidad.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reporte generado correctamente"),
            @ApiResponse(responseCode = "403", description = "No autorizado")
    })
    public ResponseEntity<List<ProductSalesReportDTO>> getTopSellingProducts() {
        List<ProductSalesReportDTO> result = productReportService.getTopSellingProducts();
        return ResponseEntity.ok(result);
    }
}
