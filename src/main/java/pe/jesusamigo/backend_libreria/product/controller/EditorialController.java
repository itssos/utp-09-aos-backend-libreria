package pe.jesusamigo.backend_libreria.product.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
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
import pe.jesusamigo.backend_libreria.product.dto.EditorialCreateDTO;
import pe.jesusamigo.backend_libreria.product.dto.EditorialResponseDTO;
import pe.jesusamigo.backend_libreria.product.service.EditorialService;

import java.util.List;

@RestController
@RequestMapping("/api/editorials")
@Tag(name = "Editoriales", description = "Operaciones relacionadas con la gestión de editoriales")
@RequiredArgsConstructor
public class EditorialController {

    private final EditorialService editorialService;

    @Operation(summary = "Crear una nueva editorial", description = "Crea una nueva editorial con los datos proporcionados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Editorial creada exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EditorialResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_EDITORIAL')")
    public ResponseEntity<EditorialResponseDTO> createEditorial(
            @Parameter(description = "Datos de la editorial a crear", required = true)
            @Valid @RequestBody EditorialCreateDTO editorialCreateDTO) {
        EditorialResponseDTO created = editorialService.create(editorialCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Listar todas las editoriales", description = "Obtiene una lista de todas las editoriales registradas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de editoriales obtenida correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EditorialResponseDTO.class)))
    })
    @GetMapping
//    @PreAuthorize("hasAuthority('GET_EDITORIALS')")
    public ResponseEntity<List<EditorialResponseDTO>> getAllEditorials() {
        return ResponseEntity.ok(editorialService.findAll());
    }

    @Operation(summary = "Obtener editorial por ID", description = "Recupera la información de una editorial específica usando su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Editorial encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EditorialResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Editorial no encontrada", content = @Content)
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('GET_EDITORIAL')")
    public ResponseEntity<EditorialResponseDTO> getEditorialById(
            @Parameter(description = "ID de la editorial a buscar", example = "1", required = true)
            @PathVariable Integer id) {
        try {
            EditorialResponseDTO dto = editorialService.findById(id);
            return ResponseEntity.ok(dto);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Actualizar una editorial existente", description = "Actualiza los datos de una editorial existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Editorial actualizada correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EditorialResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Editorial no encontrada", content = @Content)
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_EDITORIAL')")
    public ResponseEntity<EditorialResponseDTO> updateEditorial(
            @Parameter(description = "ID de la editorial a actualizar", example = "1", required = true)
            @PathVariable Integer id,
            @Parameter(description = "Datos actualizados de la editorial", required = true)
            @Valid @RequestBody EditorialCreateDTO editorialCreateDTO) {
        try {
            EditorialResponseDTO updated = editorialService.update(id, editorialCreateDTO);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar una editorial", description = "Elimina una editorial existente por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Editorial eliminada exitosamente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Editorial no encontrada", content = @Content)
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_EDITORIAL')")
    public ResponseEntity<Void> deleteEditorial(
            @Parameter(description = "ID de la editorial a eliminar", example = "1", required = true)
            @PathVariable Integer id) {
        try {
            editorialService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
