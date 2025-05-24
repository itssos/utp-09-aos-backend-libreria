package pe.jesusamigo.backend_libreria.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO para representar la información de un Producto en las respuestas.
 */
@Schema(
        name        = "ProductResponseDTO",
        description = "Datos de respuesta de un producto"
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDTO {

    @Schema(
            description = "ID único del producto",
            example     = "42"
    )
    private Integer id;

    @Schema(
            description = "Título del producto",
            example     = "Java Concurrency in Practice"
    )
    private String title;

    @Schema(
            description = "ISBN del producto",
            example     = "978-0134685991"
    )
    private String isbn;

    @Schema(
            description = "Código interno del producto",
            example     = "JAVA-001"
    )
    private String code;

    @Schema(
            description = "URL de la imagen del producto",
            example     = "https://misitio.com/imagenes/java.jpg"
    )
    private String imageUrl;

    @Schema(
            description = "Datos del autor",
            implementation = AuthorResponseDTO.class
    )
    private AuthorResponseDTO author;

    @Schema(
            description = "Datos de la categoría",
            implementation = CategoryResponseDTO.class
    )
    private CategoryResponseDTO category;

    @Schema(
            description = "Datos de la editorial",
            implementation = EditorialResponseDTO.class
    )
    private EditorialResponseDTO editorial;

    @Schema(
            description = "Precio del producto",
            example     = "49.90"
    )
    private BigDecimal price;

    @Schema(
            description = "Cantidad en stock",
            example     = "10"
    )
    private Integer stock;

    @Schema(
            description = "Descripción del producto",
            example     = "Una guía completa sobre concurrencia en Java."
    )
    private String description;

    @Schema(
            description = "Fecha de publicación",
            example     = "2018-05-10"
    )
    private LocalDate publicationDate;

    @Schema(
            description = "Indicador de si el producto está activo",
            example     = "true"
    )
    private Boolean active;
}
