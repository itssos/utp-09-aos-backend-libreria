package pe.jesusamigo.backend_libreria.sale.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.jesusamigo.backend_libreria.user.dto.UserShortResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO de respuesta de venta.
 */
@Schema(
        name = "SaleResponseDTO",
        description = "Datos de una venta registrada"
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleResponseDTO {

    @Schema(
            description = "ID único de la venta",
            example = "1001"
    )
    private Integer id;

    @Schema(
            description = "Fecha y hora de la venta",
            example = "2024-05-23T16:38:00"
    )
    private LocalDateTime saleDate;

    @Schema(
            description = "Monto total de la venta",
            example = "129.80"
    )
    @NotNull
    @DecimalMin(value = "0.01", message = "El monto total debe ser positivo")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal totalAmount;

    @Schema(
            description = "Monto pagado por el cliente",
            example = "130.00"
    )
    @NotNull
    @DecimalMin(value = "0.01", message = "El monto pagado debe ser positivo")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal amountPaid;

    @Schema(
            description = "Vuelto (cambio entregado al cliente)",
            example = "0.20"
    )
    @NotNull
    @DecimalMin(value = "0.00", message = "El vuelto no puede ser negativo")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal change;

    @Schema(
            description = "Datos del usuario vendedor",
            implementation = UserShortResponseDTO.class
    )
    @NotNull
    private UserShortResponseDTO user;

    @ArraySchema(
            schema = @Schema(implementation = SaleItemResponseDTO.class),
            minItems = 1
    )
    @NotNull
    private List<SaleItemResponseDTO> items;
}
