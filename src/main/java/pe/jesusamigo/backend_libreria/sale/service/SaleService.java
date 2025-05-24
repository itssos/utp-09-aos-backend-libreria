package pe.jesusamigo.backend_libreria.sale.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.jesusamigo.backend_libreria.product.entity.Product;
import pe.jesusamigo.backend_libreria.product.repository.ProductRepository;
import pe.jesusamigo.backend_libreria.product.service.StockUpdateService;
import pe.jesusamigo.backend_libreria.sale.dto.SaleCreateDTO;
import pe.jesusamigo.backend_libreria.sale.dto.SaleResponseDTO;
import pe.jesusamigo.backend_libreria.sale.entity.Sale;
import pe.jesusamigo.backend_libreria.sale.entity.SaleItem;
import pe.jesusamigo.backend_libreria.sale.mapper.SaleMapper;
import pe.jesusamigo.backend_libreria.sale.repository.SaleRepository;
import pe.jesusamigo.backend_libreria.user.entity.User;
import pe.jesusamigo.backend_libreria.user.repository.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SaleService {

    private final SaleRepository saleRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final SaleMapper saleMapper;
    private final StockUpdateService stockUpdateService;

    /**
     * Registra una nueva venta y descuenta el stock de los productos vendidos.
     */
    public SaleResponseDTO registerSale(SaleCreateDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + dto.getUserId()));

        List<SaleItem> items = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        // Validación de stock y cálculo de precios reales
        for (var itemDto : dto.getItems()) {
            Product product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + itemDto.getProductId()));

            // Validar stock antes de descontar
            if (product.getStock() == null || product.getStock() < itemDto.getQuantity()) {
                throw new IllegalArgumentException("Stock insuficiente para el producto " + product.getTitle());
            }

            BigDecimal unitPrice = product.getPrice();
            BigDecimal itemTotal = unitPrice.multiply(BigDecimal.valueOf(itemDto.getQuantity()));
            totalAmount = totalAmount.add(itemTotal);

            SaleItem saleItem = SaleItem.builder()
                    .product(product)
                    .quantity(itemDto.getQuantity())
                    .unitPrice(unitPrice)
                    .totalPrice(itemTotal)
                    .build();

            items.add(saleItem);
        }

        // Validar monto pagado
        if (dto.getAmountPaid() == null || dto.getAmountPaid().compareTo(totalAmount) < 0) {
            throw new IllegalArgumentException("El monto entregado no es suficiente para pagar la venta.");
        }

        // Descontar stock ahora que ya validamos todo
        for (var itemDto : dto.getItems()) {
            stockUpdateService.decreaseStockBySale(itemDto.getProductId(), itemDto.getQuantity());
        }

        BigDecimal change = dto.getAmountPaid().subtract(totalAmount);

        Sale sale = saleMapper.toEntity(
                dto,
                user,
                items,
                LocalDateTime.now(),
                totalAmount,
                dto.getAmountPaid(),
                change
        );
        items.forEach(item -> item.setSale(sale));

        Sale saved = saleRepository.save(sale);

        return saleMapper.toResponseDTO(saved);
    }

    @Transactional(readOnly = true)
    public List<SaleResponseDTO> findAll() {
        return saleRepository.findAll()
                .stream()
                .map(saleMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<SaleResponseDTO> findById(Integer id) {
        return saleRepository.findById(id)
                .map(saleMapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public List<SaleResponseDTO> findByUserId(Integer userId) {
        return saleRepository.findByUserId(userId)
                .stream()
                .map(saleMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<SaleResponseDTO> findBySaleDateBetween(LocalDateTime start, LocalDateTime end) {
        return saleRepository.findBySaleDateBetween(start, end)
                .stream()
                .map(saleMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
