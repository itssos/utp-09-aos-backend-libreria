package pe.jesusamigo.backend_libreria.report.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@Schema(description = "DTO para reporte de ventas por rango de fechas, producto y usuario")
public class SaleReportDTO {

    @Schema(description = "ID de la venta", example = "25")
    private Integer saleId;

    @Schema(description = "Fecha de la venta", example = "2024-05-25T10:30:00")
    private LocalDateTime saleDate;

    @Schema(description = "Nombre de usuario", example = "admin")
    private String username;

    @Schema(description = "ID del producto", example = "8")
    private Integer productId;

    @Schema(description = "Título del producto", example = "Biblia Reina Valera")
    private String productTitle;

    @Schema(description = "Cantidad vendida", example = "3")
    private Integer quantity;

    @Schema(description = "Precio total del producto en la venta", example = "90.00")
    private BigDecimal totalPrice;
}
