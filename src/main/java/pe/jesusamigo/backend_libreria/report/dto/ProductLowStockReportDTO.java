package pe.jesusamigo.backend_libreria.report.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "DTO para reporte de productos con stock bajo")
public class ProductLowStockReportDTO {

    @Schema(description = "ID del producto", example = "1")
    private Integer productId;

    @Schema(description = "Título del producto", example = "Biblia Reina Valera")
    private String title;

    @Schema(description = "Stock actual", example = "3")
    private Integer stock;
}
