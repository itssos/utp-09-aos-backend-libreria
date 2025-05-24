package pe.jesusamigo.backend_libreria.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para la creación de una nueva Categoría.
 */
@Schema(
        name = "CategoryCreateDTO",
        description = "Datos necesarios para crear una nueva categoría"
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryCreateDTO {

    public static final int NAME_MAX_LENGTH = 60;
    public static final int DESCRIPTION_MAX_LENGTH = 200;

    @Schema(
            description = "Nombre de la categoría",
            example     = "Electrónica",
            required    = true
    )
    @NotBlank(message = "El nombre de la categoría no puede estar vacío")
    @Size(
            max     = NAME_MAX_LENGTH,
            message = "El nombre de la categoría no puede exceder {max} caracteres"
    )
    private String name;

    @Schema(
            description = "Descripción de la categoría",
            example     = "Dispositivos electrónicos y gadgets",
            required    = false
    )
    @Size(
            max     = DESCRIPTION_MAX_LENGTH,
            message = "La descripción de la categoría no puede exceder {max} caracteres"
    )
    private String description;

    @Schema(
            description  = "Indicador de si la categoría está activa",
            example      = "true",
            defaultValue = "true"
    )
    private Boolean active = true;
}
