package pe.jesusamigo.backend_libreria.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para representar la información de una Editorial en las respuestas.
 */
@Schema(
        name = "EditorialResponseDTO",
        description = "Datos de respuesta de una Editorial"
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EditorialResponseDTO {

    @Schema(
            description = "Identificador único de la editorial",
            example = "1"
    )
    private Integer id;

    @Schema(
            description = "Nombre de la editorial",
            example = "Editorial Planeta"
    )
    private String name;

    @Schema(
            description = "Indicador de si la editorial está activa",
            example = "true"
    )
    private Boolean active;
}
