package pe.jesusamigo.backend_libreria.inventory.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.jesusamigo.backend_libreria.inventory.entity.StockMovement.MovementType;

/**
 * DTO para registrar un nuevo movimiento de stock.
 */
@Schema(
        name = "StockMovementCreateDTO",
        description = "Datos necesarios para registrar un movimiento de stock"
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockMovementCreateDTO {

    public static final int REASON_MAX_LENGTH = 200;

    @Schema(
            description = "ID del producto afectado",
            example = "12",
            required = true
    )
    @NotNull(message = "El ID del producto es obligatorio")
    private Integer productId;

    @Schema(
            description = "Tipo de movimiento: IN para entrada, OUT para salida",
            example = "IN",
            required = true,
            allowableValues = {"IN", "OUT"}
    )
    @NotNull(message = "El tipo de movimiento es obligatorio")
    private MovementType type;

    @Schema(
            description = "Cantidad afectada por el movimiento (mínimo 1)",
            example = "5",
            required = true
    )
    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos {value}")
    private Integer quantity;

    @Schema(
            description = "Motivo o comentario del movimiento",
            example = "Ajuste de inventario por daño"
    )
    @Size(max = REASON_MAX_LENGTH, message = "El motivo no puede exceder {max} caracteres")
    private String reason;
}
