package pe.jesusamigo.backend_libreria.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para representar la información de una Categoría en las respuestas.
 */
@Schema(
        name        = "CategoryResponseDTO",
        description = "Datos de respuesta de una categoría"
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponseDTO {

    @Schema(
            description = "Identificador único de la categoría",
            example     = "1"
    )
    private Integer id;

    @Schema(
            description = "Nombre de la categoría",
            example     = "Electrónica"
    )
    private String name;

    @Schema(
            description = "Descripción de la categoría",
            example     = "Dispositivos electrónicos y gadgets"
    )
    private String description;

    @Schema(
            description = "Indicador de si la categoría está activa",
            example     = "true"
    )
    private Boolean active;
}
