package pe.jesusamigo.backend_libreria.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO para la creación de un nuevo Producto.
 */
@Schema(
        name        = "ProductCreateDTO",
        description = "Datos necesarios para crear un nuevo producto"
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCreateDTO {

    // === Constantes de validación ===
    public static final int TITLE_MAX_LENGTH       = 120;
    public static final int ISBN_MAX_LENGTH        = 20;
    public static final int CODE_MAX_LENGTH        = 50;
    public static final int IMAGE_URL_MAX_LENGTH   = 500;
    public static final int DESCRIPTION_MAX_LENGTH = 500;

    @Schema(
            description = "Título del producto",
            example     = "Java Concurrency in Practice",
            required    = true
    )
    @NotBlank(message = "El título no puede estar vacío")
    @Size(
            max     = TITLE_MAX_LENGTH,
            message = "El título no puede exceder {max} caracteres"
    )
    private String title;

    @Schema(
            description = "ISBN del producto (solo números y guiones)",
            example     = "978-0134685991",
            required    = true
    )
    @NotBlank(message = "El ISBN no puede estar vacío")
    @Size(
            max     = ISBN_MAX_LENGTH,
            message = "El ISBN no puede exceder {max} caracteres"
    )
    @Pattern(
            regexp  = "^[0-9\\-]+$",
            message = "El ISBN solo puede contener números y guiones"
    )
    private String isbn;

    @Schema(
            description = "Código interno del producto (alfanumérico y guiones)",
            example     = "JAVA-001",
            required    = true
    )
    @NotBlank(message = "El código no puede estar vacío")
    @Size(
            max     = CODE_MAX_LENGTH,
            message = "El código no puede exceder {max} caracteres"
    )
    @Pattern(
            regexp  = "^[A-Z0-9\\-]+$",
            message = "El código solo puede contener letras mayúsculas, números y guiones"
    )
    private String code;

    @Schema(
            description = "URL de la imagen del producto",
            example     = "https://misitio.com/imagenes/java.jpg"
    )
    @Size(
            max     = IMAGE_URL_MAX_LENGTH,
            message = "La URL de la imagen no puede exceder {max} caracteres"
    )
    private String imageUrl;

    @Schema(
            description = "ID del autor asociado",
            example     = "3",
            required    = true
    )
    @NotNull(message = "El ID del autor es obligatorio")
    private Integer authorId;

    @Schema(
            description = "ID de la categoría asociada",
            example     = "5",
            required    = true
    )
    @NotNull(message = "El ID de la categoría es obligatorio")
    private Integer categoryId;

    @Schema(
            description = "ID de la editorial asociada",
            example     = "2",
            required    = true
    )
    @NotNull(message = "El ID de la editorial es obligatorio")
    private Integer editorialId;

    @Schema(
            description = "Precio del producto (mínimo 0.01)",
            example     = "49.90",
            required    = true
    )
    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(
            value   = "0.01",
            message = "El precio debe ser al menos {value}"
    )
    @Digits(
            integer = 8,
            fraction= 2,
            message = "El precio no puede tener más de {integer} dígitos enteros y {fraction} decimales"
    )
    private BigDecimal price;

    @Schema(
            description = "Cantidad en stock (mínimo 0)",
            example     = "10",
            required    = true
    )
    @NotNull(message = "El stock es obligatorio")
    @Min(
            value   = 0,
            message = "El stock no puede ser negativo"
    )
    private Integer stock;

    @Schema(
            description = "Descripción del producto",
            example     = "Una guía completa sobre concurrencia en Java."
    )
    @Size(
            max     = DESCRIPTION_MAX_LENGTH,
            message = "La descripción no puede exceder {max} caracteres"
    )
    private String description;

    @Schema(
            description = "Fecha de publicación (no puede ser futura)",
            example     = "2018-05-10"
    )
    @PastOrPresent(message = "La fecha de publicación no puede ser futura")
    private LocalDate publicationDate;

    @Schema(
            description  = "Indicador de si el producto está activo",
            example      = "true",
            defaultValue = "true"
    )
    private Boolean active = true;
}
