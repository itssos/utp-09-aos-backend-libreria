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
import pe.jesusamigo.backend_libreria.product.dto.CategoryCreateDTO;
import pe.jesusamigo.backend_libreria.product.dto.CategoryResponseDTO;
import pe.jesusamigo.backend_libreria.product.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Categorías", description = "Operaciones relacionadas con la gestión de categorías")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "Crear una nueva categoría", description = "Crea una nueva categoría con los datos proporcionados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoría creada exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_CATEGORY')")
    public ResponseEntity<CategoryResponseDTO> createCategory(
            @Parameter(description = "Datos de la categoría a crear", required = true)
            @Valid @RequestBody CategoryCreateDTO categoryCreateDTO) {
        CategoryResponseDTO created = categoryService.create(categoryCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Listar todas las categorías", description = "Obtiene una lista de todas las categorías registradas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de categorías obtenida correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryResponseDTO.class)))
    })
    @GetMapping
//    @PreAuthorize("hasAuthority('GET_CATEGORIES')")
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @Operation(summary = "Obtener categoría por ID", description = "Recupera la información de una categoría específica usando su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoría encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada", content = @Content)
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('GET_CATEGORY')")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(
            @Parameter(description = "ID de la categoría a buscar", example = "1", required = true)
            @PathVariable Integer id) {
        return categoryService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar una categoría existente", description = "Actualiza los datos de una categoría existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoría actualizada correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada", content = @Content)
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_CATEGORY')")
    public ResponseEntity<CategoryResponseDTO> updateCategory(
            @Parameter(description = "ID de la categoría a actualizar", example = "1", required = true)
            @PathVariable Integer id,
            @Parameter(description = "Datos actualizados de la categoría", required = true)
            @Valid @RequestBody CategoryCreateDTO categoryCreateDTO) {
        return categoryService.update(id, categoryCreateDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar una categoría", description = "Elimina una categoría existente por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Categoría eliminada exitosamente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada", content = @Content)
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_CATEGORY')")
    public ResponseEntity<Void> deleteCategory(
            @Parameter(description = "ID de la categoría a eliminar", example = "1", required = true)
            @PathVariable Integer id) {
        boolean deleted = categoryService.delete(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
