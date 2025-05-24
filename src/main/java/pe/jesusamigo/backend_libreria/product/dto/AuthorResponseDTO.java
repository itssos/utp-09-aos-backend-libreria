package pe.jesusamigo.backend_libreria.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para representar la información de un Autor en las respuestas.
 */
@Schema(
        name        = "AuthorResponseDTO",
        description = "Datos de respuesta de un autor"
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorResponseDTO {

    @Schema(
            description = "Identificador único del autor",
            example     = "1"
    )
    private Integer id;

    @Schema(
            description = "Nombre completo del autor",
            example     = "Gabriel García Márquez"
    )
    private String name;

    @Schema(
            description = "Biografía breve del autor",
            example     = "Escritor y periodista colombiano, autor de 'Cien años de soledad'."
    )
    private String bio;

    @Schema(
            description = "Indicador de si el autor está activo",
            example     = "true"
    )
    private Boolean active;
}
