package pe.jesusamigo.backend_libreria.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para la creación de una nueva Editorial.
 */
@Schema(
        name = "EditorialCreateDTO",
        description = "Datos necesarios para crear una nueva Editorial"
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EditorialCreateDTO {

    public static final int NAME_MAX_LENGTH = 80;

    @Schema(
            description = "Nombre de la editorial",
            example = "Editorial Planeta",
            required = true
    )
    @NotBlank(message = "El nombre de la editorial no puede estar vacío")
    @Size(
            max = NAME_MAX_LENGTH,
            message = "El nombre de la editorial no puede exceder {max} caracteres"
    )
    private String name;

    @Schema(
            description = "Indicador de si la editorial está activa",
            example = "true",
            defaultValue = "true"
    )
    private Boolean active = true;
}
