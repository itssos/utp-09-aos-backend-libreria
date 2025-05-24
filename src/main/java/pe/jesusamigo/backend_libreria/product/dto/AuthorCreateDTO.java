package pe.jesusamigo.backend_libreria.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para la creación de un nuevo Autor.
 */
@Schema(
        name        = "AuthorCreateDTO",
        description = "Datos necesarios para crear un nuevo autor"
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorCreateDTO {

    public static final int NAME_MAX_LENGTH = 80;
    public static final int BIO_MAX_LENGTH  = 250;

    @Schema(
            description = "Nombre completo del autor",
            example     = "Gabriel García Márquez",
            required    = true
    )
    @NotBlank(message = "El nombre del autor no puede estar vacío")
    @Size(
            max     = NAME_MAX_LENGTH,
            message = "El nombre del autor no puede exceder {max} caracteres"
    )
    private String name;

    @Schema(
            description = "Biografía breve del autor",
            example     = "Escritor y periodista colombiano, autor de 'Cien años de soledad'.",
            required    = false
    )
    @Size(
            max     = BIO_MAX_LENGTH,
            message = "La biografía no puede exceder {max} caracteres"
    )
    private String bio;

    @Schema(
            description  = "Indicador de si el autor está activo",
            example      = "true",
            defaultValue = "true"
    )
    private Boolean active = true;
}
