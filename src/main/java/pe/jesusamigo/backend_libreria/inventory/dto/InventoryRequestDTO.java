package pe.jesusamigo.backend_libreria.inventory.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
        name = "InventoryRequestDTO",
        description = "Datos para recargar o dar de baja inventario"
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryRequestDTO {

    @Schema(
            description = "ID del producto",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "El ID del producto es obligatorio")
    private Integer productId;

    @Schema(
            description = "Cantidad de unidades a afectar (mínimo 1)",
            example = "10",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser mayor que cero")
    private Integer quantity;

    @Schema(
            description = "Motivo de la operación (opcional)",
            example = "Reposición por compra"
    )
    private String reason;
}
