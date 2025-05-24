package pe.jesusamigo.backend_libreria.sale.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO para crear un ítem/detalle de venta.
 */
@Schema(
        name = "SaleItemCreateDTO",
        description = "Detalle de producto vendido en la venta"
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleItemCreateDTO {

    @Schema(
            description = "ID del producto vendido",
            example = "11",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "El ID del producto es obligatorio")
    private Integer productId;

    @Schema(
            description = "Cantidad vendida (mínimo 1)",
            example = "2",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser mayor o igual a 1")
    private Integer quantity;

}
