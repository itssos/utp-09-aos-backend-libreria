package pe.jesusamigo.backend_libreria.sale.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.jesusamigo.backend_libreria.product.dto.ProductShortResponseDTO;

import java.math.BigDecimal;

/**
 * DTO de respuesta de detalle de venta.
 */
@Schema(
        name = "SaleItemResponseDTO",
        description = "Detalle de un producto vendido en la venta"
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleItemResponseDTO {

    @Schema(
            description = "ID único del ítem de venta",
            example = "501"
    )
    private Integer id;

    @Schema(
            description = "Datos básicos del producto vendido",
            implementation = ProductShortResponseDTO.class
    )
    private ProductShortResponseDTO product;

    @Schema(
            description = "Cantidad vendida",
            example = "2"
    )
    private Integer quantity;

    @Schema(
            description = "Precio unitario",
            example = "59.90"
    )
    private BigDecimal unitPrice;

    @Schema(
            description = "Precio total del ítem",
            example = "119.80"
    )
    private BigDecimal totalPrice;
}
