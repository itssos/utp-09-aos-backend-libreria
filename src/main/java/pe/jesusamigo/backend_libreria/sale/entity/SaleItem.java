package pe.jesusamigo.backend_libreria.sale.entity;

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
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pe.jesusamigo.backend_libreria.product.entity.Product;

import java.math.BigDecimal;

@Entity
@Table(name = SaleItem.TABLE_NAME)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class SaleItem {

    public static final String TABLE_NAME = "sale_items";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_UNIT_PRICE = "unit_price";
    public static final String COLUMN_TOTAL_PRICE = "total_price";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_ID)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sale_id", nullable = false)
    private Sale sale;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @NotNull
    @Min(1)
    @Column(name = COLUMN_QUANTITY, nullable = false)
    private Integer quantity;

    @NotNull
    @DecimalMin(value = "0.01", message = "El precio unitario debe ser positivo")
    @Digits(integer = 8, fraction = 2)
    @Column(name = COLUMN_UNIT_PRICE, nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @NotNull
    @DecimalMin(value = "0.01", message = "El precio total debe ser positivo")
    @Digits(integer = 10, fraction = 2)
    @Column(name = COLUMN_TOTAL_PRICE, nullable = false, precision = 12, scale = 2)
    private BigDecimal totalPrice;
}
