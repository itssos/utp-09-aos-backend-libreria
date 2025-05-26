package pe.jesusamigo.backend_libreria.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.jesusamigo.backend_libreria.user.dto.UserShortResponseDTO;
import pe.jesusamigo.backend_libreria.user.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Usuarios", description = "Gestión de usuarios")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Listar usuarios (solo datos básicos)",
            description = "Obtiene una lista de usuarios mostrando solo los datos clave (id, username, fullName)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserShortResponseDTO.class)))
    })
    @GetMapping("/short")
    @PreAuthorize("hasAuthority('GET_USERS')")
    public ResponseEntity<List<UserShortResponseDTO>> getAllUsersShort() {
        List<UserShortResponseDTO> users = userService.getAllUsersShort();
        return ResponseEntity.ok(users);
    }

}
