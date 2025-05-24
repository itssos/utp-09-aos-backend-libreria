package pe.jesusamigo.backend_libreria.inventory.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import pe.jesusamigo.backend_libreria.product.entity.Product;

import java.time.LocalDateTime;

@Entity
@Table(name = StockMovement.TABLE_NAME)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class StockMovement {

    public static final String TABLE_NAME = "stock_movements";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PRODUCT_ID = "product_id";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_MOVEMENT_DATE = "movement_date";
    public static final String COLUMN_REASON = "reason";
    public static final int REASON_MAX_LENGTH = 200;

    public enum MovementType {
        IN, OUT
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_ID)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = COLUMN_PRODUCT_ID, nullable = false)
    private Product product;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = COLUMN_TYPE, nullable = false, length = 10)
    private MovementType type;

    @NotNull
    @Min(1)
    @Column(name = COLUMN_QUANTITY, nullable = false)
    private Integer quantity;

    @NotNull
    @Column(name = COLUMN_MOVEMENT_DATE, nullable = false)
    private LocalDateTime movementDate;

    @Size(max = REASON_MAX_LENGTH)
    @Column(name = COLUMN_REASON, length = REASON_MAX_LENGTH)
    private String reason;
}
