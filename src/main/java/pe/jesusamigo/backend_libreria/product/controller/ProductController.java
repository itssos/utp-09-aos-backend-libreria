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
import pe.jesusamigo.backend_libreria.product.dto.ProductCreateDTO;
import pe.jesusamigo.backend_libreria.product.dto.ProductResponseDTO;
import pe.jesusamigo.backend_libreria.product.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Productos", description = "Operaciones relacionadas con la gestión de productos/libros")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Crear un nuevo producto", description = "Crea un nuevo producto/libro con sus relaciones asignadas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto creado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o entidades relacionadas no encontradas", content = @Content)
    })
    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_PRODUCT')")
    public ResponseEntity<?> createProduct(
            @Parameter(description = "Datos del producto a crear", required = true)
            @Valid @RequestBody ProductCreateDTO productCreateDTO) {
        try {
            ProductResponseDTO created = productService.create(productCreateDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @Operation(summary = "Listar todos los productos", description = "Obtiene una lista de todos los productos/libros registrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de productos obtenida correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponseDTO.class)))
    })
    @GetMapping
    @PreAuthorize("hasAuthority('GET_PRODUCTS')")
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.findAll());
    }

    @Operation(summary = "Obtener producto por ID", description = "Recupera la información de un producto específico usando su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('GET_PRODUCT')")
    public ResponseEntity<ProductResponseDTO> getProductById(
            @Parameter(description = "ID del producto a buscar", example = "1", required = true)
            @PathVariable Integer id) {
        return productService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar un producto existente", description = "Actualiza los datos de un producto/libro existente y sus relaciones.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o entidades relacionadas no encontradas", content = @Content),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_PRODUCT')")
    public ResponseEntity<?> updateProduct(
            @Parameter(description = "ID del producto a actualizar", example = "1", required = true)
            @PathVariable Integer id,
            @Parameter(description = "Datos actualizados del producto", required = true)
            @Valid @RequestBody ProductCreateDTO productCreateDTO) {
        try {
            return productService.update(id, productCreateDTO)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @Operation(summary = "Eliminar un producto", description = "Elimina un producto existente por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_PRODUCT')")
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "ID del producto a eliminar", example = "1", required = true)
            @PathVariable Integer id) {
        boolean deleted = productService.delete(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
