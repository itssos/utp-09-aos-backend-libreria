package pe.jesusamigo.backend_libreria.sale.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pe.jesusamigo.backend_libreria.user.entity.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = Sale.TABLE_NAME)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class Sale {

    public static final String TABLE_NAME = "sales";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TOTAL_AMOUNT = "total_amount";
    public static final String COLUMN_SALE_DATE = "sale_date";
    public static final String COLUMN_CUSTOMER_NAME = "customer_name";
    public static final String COLUMN_AMOUNT_PAID = "amount_paid";
    public static final String COLUMN_CHANGE = "change_amount";
    public static final int CUSTOMER_NAME_MAX_LENGTH = 100;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_ID)
    private Integer id;

    @NotNull
    @Column(name = COLUMN_SALE_DATE, nullable = false)
    private LocalDateTime saleDate;

    @NotNull
    @DecimalMin(value = "0.01", message = "El monto total debe ser positivo")
    @Digits(integer = 10, fraction = 2)
    @Column(name = COLUMN_TOTAL_AMOUNT, nullable = false, precision = 12, scale = 2)
    private BigDecimal totalAmount;

    @NotNull
    @DecimalMin(value = "0.01", message = "El monto pagado debe ser positivo")
    @Digits(integer = 10, fraction = 2)
    @Column(name = COLUMN_AMOUNT_PAID, nullable = false, precision = 12, scale = 2)
    private BigDecimal amountPaid;

    @NotNull
    @DecimalMin(value = "0.00", message = "El cambio no puede ser negativo")
    @Digits(integer = 10, fraction = 2)
    @Column(name = COLUMN_CHANGE, nullable = false, precision = 12, scale = 2)
    private BigDecimal change;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SaleItem> items;
}