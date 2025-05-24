package pe.jesusamigo.backend_libreria.sale.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO para crear una venta.
 */
@Schema(
        name = "SaleCreateDTO",
        description = "Datos necesarios para registrar una nueva venta"
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleCreateDTO {

    @Schema(
            description = "ID del usuario vendedor",
            example = "5",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "El ID del usuario es obligatorio")
    private Integer userId;

    @Schema(description = "Monto entregado por el cliente", example = "200.00", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El monto pagado es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto pagado debe ser positivo")
    private BigDecimal amountPaid;

    @ArraySchema(
            schema = @Schema(implementation = SaleItemCreateDTO.class),
            minItems = 1,
            uniqueItems = false
    )
    @NotNull(message = "Debe registrar al menos un ítem en la venta")
    @Size(min = 1, message = "Debe registrar al menos un ítem en la venta")
    private List<SaleItemCreateDTO> items;
}
