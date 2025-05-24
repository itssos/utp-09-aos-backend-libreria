package pe.jesusamigo.backend_libreria.inventory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.jesusamigo.backend_libreria.inventory.dto.InventoryRequestDTO;
import pe.jesusamigo.backend_libreria.inventory.dto.StockMovementCreateDTO;
import pe.jesusamigo.backend_libreria.inventory.dto.StockMovementResponseDTO;
import pe.jesusamigo.backend_libreria.inventory.entity.StockMovement.MovementType;
import pe.jesusamigo.backend_libreria.product.entity.Product;
import pe.jesusamigo.backend_libreria.product.repository.ProductRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryAdjustmentService {

    private final StockMovementService stockMovementService;
    private final ProductRepository productRepository;

    public StockMovementResponseDTO rechargeStock(InventoryRequestDTO request) {
        validateProductExists(request.getProductId());
        validateQuantity(request.getQuantity());
        StockMovementCreateDTO dto = StockMovementCreateDTO.builder()
                .productId(request.getProductId())
                .type(MovementType.IN)
                .quantity(request.getQuantity())
                .reason(request.getReason())
                .build();
        return stockMovementService.create(dto);
    }

    public StockMovementResponseDTO decreaseStock(InventoryRequestDTO request) {
        validateProductExists(request.getProductId());
        validateQuantity(request.getQuantity());
        Integer currentStock = productRepository.findById(request.getProductId())
                .map(Product::getStock)
                .orElse(0);
        if (currentStock < request.getQuantity()) {
            throw new IllegalArgumentException("Stock insuficiente para la operación.");
        }
        StockMovementCreateDTO dto = StockMovementCreateDTO.builder()
                .productId(request.getProductId())
                .type(MovementType.OUT)
                .quantity(request.getQuantity())
                .reason(request.getReason())
                .build();
        return stockMovementService.create(dto);
    }

    @Transactional(readOnly = true)
    public Integer getStockByProductId(Integer productId) {
        validateProductExists(productId);
        return productRepository.findById(productId)
                .map(Product::getStock)
                .orElse(0);
    }

    private void validateProductExists(Integer productId) {
        if (productId == null) {
            throw new IllegalArgumentException("El ID del producto es obligatorio.");
        }
        if (!productRepository.existsById(productId)) {
            throw new IllegalArgumentException("Producto no encontrado con ID: " + productId);
        }
    }

    private void validateQuantity(Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }
    }
}
