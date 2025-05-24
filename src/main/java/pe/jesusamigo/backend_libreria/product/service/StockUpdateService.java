package pe.jesusamigo.backend_libreria.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.jesusamigo.backend_libreria.product.entity.Product;
import pe.jesusamigo.backend_libreria.product.repository.ProductRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class StockUpdateService {

    private final ProductRepository productRepository;

    /**
     * Disminuye el stock de un producto según una cantidad vendida.
     * Lanza IllegalArgumentException si no hay suficiente stock o el producto no existe.
     *
     * @param productId ID del producto a actualizar
     * @param quantity  Cantidad vendida (debe ser mayor que cero)
     */
    public void decreaseStockBySale(Integer productId, Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("La cantidad a descontar debe ser mayor que cero.");
        }
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + productId));
        Integer currentStock = product.getStock() != null ? product.getStock() : 0;
        if (currentStock < quantity) {
            throw new IllegalArgumentException("Stock insuficiente para completar la venta.");
        }
        product.setStock(currentStock - quantity);
        productRepository.save(product);
    }
}
