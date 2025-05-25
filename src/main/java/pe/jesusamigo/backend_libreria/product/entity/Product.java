package pe.jesusamigo.backend_libreria.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = Product.TABLE_NAME)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class Product {

    // === Constantes de la entidad ===
    public static final String TABLE_NAME = "products";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_ISBN = "isbn";
    public static final String COLUMN_CODE = "code";
    public static final String COLUMN_IMAGE_URL = "image_url";
    public static final int TITLE_MAX_LENGTH = 120;
    public static final int ISBN_MAX_LENGTH = 20;
    public static final int CODE_MAX_LENGTH = 50;
    public static final int IMAGE_URL_MAX_LENGTH = 500;
    public static final int DESCRIPTION_MAX_LENGTH = 500;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_ID)
    private Integer id;

    @NotBlank
    @Size(max = TITLE_MAX_LENGTH)
    @Column(name = COLUMN_TITLE, nullable = false, length = TITLE_MAX_LENGTH)
    private String title;

    @NotBlank
    @Size(max = ISBN_MAX_LENGTH)
    @Pattern(regexp = "^[0-9\\-]+$", message = "ISBN must contain only numbers and hyphens")
    @Column(name = COLUMN_ISBN, nullable = false, length = ISBN_MAX_LENGTH, unique = true)
    private String isbn;

    @NotBlank
    @Size(max = CODE_MAX_LENGTH)
    @Pattern(regexp = "^[A-Z0-9\\-]+$", message = "Code must be alphanumeric and may include hyphens")
    @Column(name = COLUMN_CODE, nullable = false, length = CODE_MAX_LENGTH, unique = true)
    private String code;

    @Size(max = IMAGE_URL_MAX_LENGTH)
    @Column(name = COLUMN_IMAGE_URL, length = IMAGE_URL_MAX_LENGTH)
    private String imageUrl;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "editorial_id", nullable = false)
    private Editorial editorial;

    @NotNull
    @DecimalMin(value = "0.01", inclusive = true)
    @Digits(integer = 8, fraction = 2)
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @NotNull
    @Min(0)
    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Size(max = DESCRIPTION_MAX_LENGTH)
    @Column(name = "description", length = DESCRIPTION_MAX_LENGTH)
    private String description;

    @PastOrPresent
    @Column(name = "publication_date")
    private LocalDate publicationDate;

    @NotNull
    @Builder.Default
    @Column(name = "active", nullable = false)
    private Boolean active = true;
}
