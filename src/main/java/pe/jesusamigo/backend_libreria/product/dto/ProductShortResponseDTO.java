package pe.jesusamigo.backend_libreria.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO reducido para mostrar datos clave del producto en relaciones.
 */
@Schema(
        name = "ProductShortResponseDTO",
        description = "Datos básicos del producto para relaciones"
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductShortResponseDTO {

    @Schema(
            description = "ID único del producto",
            example = "42"
    )
    private Integer id;

    @Schema(
            description = "Título del producto",
            example = "Java Concurrency in Practice"
    )
    private String title;
}
