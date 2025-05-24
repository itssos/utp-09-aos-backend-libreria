package pe.jesusamigo.backend_libreria.product.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.jesusamigo.backend_libreria.product.dto.AuthorCreateDTO;
import pe.jesusamigo.backend_libreria.product.dto.AuthorResponseDTO;
import pe.jesusamigo.backend_libreria.product.service.AuthorService;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@Tag(name = "Autores", description = "Operaciones relacionadas con la gestión de autores")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @Operation(summary = "Crear un nuevo autor", description = "Crea un nuevo autor con los datos proporcionados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Autor creado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthorResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_AUTHOR')")
    public ResponseEntity<AuthorResponseDTO> createAuthor(
            @Parameter(description = "Datos del autor a crear", required = true)
            @Valid @RequestBody AuthorCreateDTO authorCreateDTO) {
        AuthorResponseDTO created = authorService.create(authorCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Listar todos los autores", description = "Obtiene una lista de todos los autores registrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de autores obtenida correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthorResponseDTO.class)))
    })
    @GetMapping
    @PreAuthorize("hasAuthority('GET_AUTHORS')")
    public ResponseEntity<List<AuthorResponseDTO>> getAllAuthors() {
        return ResponseEntity.ok(authorService.findAll());
    }

    @Operation(summary = "Obtener autor por ID", description = "Recupera la información de un autor específico usando su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autor encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Autor no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('GET_AUTHOR')")
    public ResponseEntity<AuthorResponseDTO> getAuthorById(
            @Parameter(description = "ID del autor a buscar", example = "1", required = true)
            @PathVariable Integer id) {
        return authorService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar un autor existente", description = "Actualiza los datos de un autor existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autor actualizado correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthorResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Autor no encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_AUTHOR')")
    public ResponseEntity<AuthorResponseDTO> updateAuthor(
            @Parameter(description = "ID del autor a actualizar", example = "1", required = true)
            @PathVariable Integer id,
            @Parameter(description = "Datos actualizados del autor", required = true)
            @Valid @RequestBody AuthorCreateDTO authorCreateDTO) {
        return authorService.update(id, authorCreateDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar un autor", description = "Elimina un autor existente por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Autor eliminado exitosamente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Autor no encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_AUTHOR')")
    public ResponseEntity<Void> deleteAuthor(
            @Parameter(description = "ID del autor a eliminar", example = "1", required = true)
            @PathVariable Integer id) {
        boolean deleted = authorService.delete(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
