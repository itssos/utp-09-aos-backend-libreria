package pe.jesusamigo.backend_libreria.inventory.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.jesusamigo.backend_libreria.inventory.entity.StockMovement.MovementType;
import pe.jesusamigo.backend_libreria.product.dto.ProductShortResponseDTO;

import java.time.LocalDateTime;

/**
 * DTO de respuesta para movimientos de stock.
 */
@Schema(
        name = "StockMovementResponseDTO",
        description = "Datos de respuesta de un movimiento de stock"
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockMovementResponseDTO {

    @Schema(
            description = "ID único del movimiento",
            example = "1001",
            readOnly = true
    )
    private Integer id;

    @Schema(
            description = "Datos clave del producto afectado",
            implementation = ProductShortResponseDTO.class
    )
    private ProductShortResponseDTO product;

    @Schema(
            description = "Tipo de movimiento: IN o OUT",
            example = "IN"
    )
    private MovementType type;

    @Schema(
            description = "Cantidad afectada por el movimiento",
            example = "5"
    )
    private Integer quantity;

    @Schema(
            description = "Fecha y hora del movimiento",
            example = "2024-05-23T17:32:11",
            readOnly = true
    )
    private LocalDateTime movementDate;

    @Schema(
            description = "Motivo o comentario del movimiento",
            example = "Ajuste de inventario por daño"
    )
    private String reason;
}
