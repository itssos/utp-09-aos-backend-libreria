package pe.jesusamigo.backend_libreria.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = Editorial.TABLE_NAME)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class Editorial {

    public static final String TABLE_NAME = "editorials";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final int NAME_MAX_LENGTH = 80;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_ID)
    private Integer id;

    @NotBlank
    @Size(max = NAME_MAX_LENGTH)
    @Column(name = COLUMN_NAME, nullable = false, length = NAME_MAX_LENGTH, unique = true)
    private String name;

    @Builder.Default
    @Column(name = "active", nullable = false)
    private Boolean active = true;
}