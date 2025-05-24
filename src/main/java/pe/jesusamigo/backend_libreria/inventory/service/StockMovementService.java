package pe.jesusamigo.backend_libreria.inventory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.jesusamigo.backend_libreria.inventory.dto.StockMovementCreateDTO;
import pe.jesusamigo.backend_libreria.inventory.dto.StockMovementResponseDTO;
import pe.jesusamigo.backend_libreria.inventory.entity.StockMovement;
import pe.jesusamigo.backend_libreria.inventory.mapper.StockMovementMapper;
import pe.jesusamigo.backend_libreria.inventory.repository.StockMovementRepository;
import pe.jesusamigo.backend_libreria.product.entity.Product;
import pe.jesusamigo.backend_libreria.product.repository.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StockMovementService {

    private final StockMovementRepository stockMovementRepository;
    private final ProductRepository productRepository;
    private final StockMovementMapper stockMovementMapper;

    /**
     * Registra un nuevo movimiento de stock y actualiza el stock del producto.
     */
    public StockMovementResponseDTO create(StockMovementCreateDTO dto) {
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + dto.getProductId()));

        StockMovement movement = stockMovementMapper.toEntity(dto);
        movement.setProduct(product);

        // Actualización automática de stock en el producto
        Integer currentStock = product.getStock() != null ? product.getStock() : 0;
        int quantity = dto.getQuantity() != null ? dto.getQuantity() : 0;

        switch (dto.getType()) {
            case IN -> product.setStock(currentStock + quantity);
            case OUT -> {
                if (currentStock < quantity) {
                    throw new IllegalArgumentException("Stock insuficiente para realizar la salida");
                }
                product.setStock(currentStock - quantity);
            }
            default -> throw new IllegalArgumentException("Tipo de movimiento no soportado");
        }

        productRepository.save(product);
        StockMovement saved = stockMovementRepository.save(movement);
        return stockMovementMapper.toResponseDTO(saved);
    }

    /**
     * Actualiza un movimiento de stock existente y ajusta el stock del producto.
     */
    public Optional<StockMovementResponseDTO> update(Integer id, StockMovementCreateDTO dto) {
        return stockMovementRepository.findById(id).map(existing -> {
            Product oldProduct = existing.getProduct();
            int oldQuantity = existing.getQuantity();
            StockMovement.MovementType oldType = existing.getType();

            // Deshacer el efecto anterior sobre el stock
            if (oldType == StockMovement.MovementType.IN) {
                oldProduct.setStock(oldProduct.getStock() - oldQuantity);
            } else if (oldType == StockMovement.MovementType.OUT) {
                oldProduct.setStock(oldProduct.getStock() + oldQuantity);
            }

            productRepository.save(oldProduct);

            // Aplicar el nuevo efecto
            Product newProduct = productRepository.findById(dto.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + dto.getProductId()));

            int newQuantity = dto.getQuantity() != null ? dto.getQuantity() : 0;
            switch (dto.getType()) {
                case IN -> newProduct.setStock(newProduct.getStock() + newQuantity);
                case OUT -> {
                    if (newProduct.getStock() < newQuantity) {
                        throw new IllegalArgumentException("Stock insuficiente para realizar la salida");
                    }
                    newProduct.setStock(newProduct.getStock() - newQuantity);
                }
                default -> throw new IllegalArgumentException("Tipo de movimiento no soportado");
            }

            existing.setProduct(newProduct);
            existing.setType(dto.getType());
            existing.setQuantity(dto.getQuantity());
            existing.setReason(dto.getReason());
            existing.setMovementDate(java.time.LocalDateTime.now());

            productRepository.save(newProduct);
            StockMovement updated = stockMovementRepository.save(existing);
            return stockMovementMapper.toResponseDTO(updated);
        });
    }

    /**
     * Lista todos los movimientos de stock.
     */
    @Transactional(readOnly = true)
    public List<StockMovementResponseDTO> findAll() {
        return stockMovementRepository.findAll()
                .stream()
                .map(stockMovementMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Lista los movimientos de stock de un producto específico.
     */
    @Transactional(readOnly = true)
    public List<StockMovementResponseDTO> findByProductId(Integer productId) {
        return stockMovementRepository.findByProductId(productId)
                .stream()
                .map(stockMovementMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca un movimiento de stock por su ID.
     */
    @Transactional(readOnly = true)
    public Optional<StockMovementResponseDTO> findById(Integer id) {
        return stockMovementRepository.findById(id)
                .map(stockMovementMapper::toResponseDTO);
    }
}
