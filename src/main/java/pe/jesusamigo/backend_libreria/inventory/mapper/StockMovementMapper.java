package pe.jesusamigo.backend_libreria.inventory.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pe.jesusamigo.backend_libreria.inventory.dto.StockMovementCreateDTO;
import pe.jesusamigo.backend_libreria.inventory.dto.StockMovementResponseDTO;
import pe.jesusamigo.backend_libreria.inventory.entity.StockMovement;
import pe.jesusamigo.backend_libreria.product.dto.ProductShortResponseDTO;
import pe.jesusamigo.backend_libreria.product.entity.Product;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class StockMovementMapper {

    public StockMovement toEntity(StockMovementCreateDTO dto) {
        return StockMovement.builder()
                .product(Product.builder().id(dto.getProductId()).build())
                .type(dto.getType())
                .quantity(dto.getQuantity())
                .reason(dto.getReason())
                .movementDate(LocalDateTime.now())
                .build();
    }

    public StockMovementResponseDTO toResponseDTO(StockMovement entity) {
        Product product = entity.getProduct();
        ProductShortResponseDTO productShort = ProductShortResponseDTO.builder()
                .id(product.getId())
                .title(product.getTitle())
                .build();

        return StockMovementResponseDTO.builder()
                .id(entity.getId())
                .product(productShort)
                .type(entity.getType())
                .quantity(entity.getQuantity())
                .movementDate(entity.getMovementDate())
                .reason(entity.getReason())
                .build();
    }
}
