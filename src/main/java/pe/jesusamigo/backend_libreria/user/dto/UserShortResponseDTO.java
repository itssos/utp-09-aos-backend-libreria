package pe.jesusamigo.backend_libreria.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO reducido para mostrar datos clave del usuario (por ejemplo, vendedor en una venta).
 */
@Schema(
        name = "UserShortResponseDTO",
        description = "Datos básicos del usuario para relaciones"
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserShortResponseDTO {

    @Schema(
            description = "ID único del usuario",
            example = "5"
    )
    private Integer id;

    @Schema(
            description = "Nombre de usuario",
            example = "epalomino"
    )
    private String username;

    @Schema(
            description = "Nombre completo del usuario",
            example = "Eduardo Palomino"
    )
    private String fullName;
}
