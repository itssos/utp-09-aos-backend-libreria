package pe.jesusamigo.backend_libreria.sale.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pe.jesusamigo.backend_libreria.persons.person.entity.Person;
import pe.jesusamigo.backend_libreria.persons.person.repository.PersonRepository;
import pe.jesusamigo.backend_libreria.product.dto.ProductShortResponseDTO;
import pe.jesusamigo.backend_libreria.product.entity.Product;
import pe.jesusamigo.backend_libreria.sale.dto.SaleCreateDTO;
import pe.jesusamigo.backend_libreria.sale.dto.SaleItemResponseDTO;
import pe.jesusamigo.backend_libreria.sale.dto.SaleResponseDTO;
import pe.jesusamigo.backend_libreria.sale.entity.Sale;
import pe.jesusamigo.backend_libreria.sale.entity.SaleItem;
import pe.jesusamigo.backend_libreria.user.dto.UserShortResponseDTO;
import pe.jesusamigo.backend_libreria.user.entity.User;
import pe.jesusamigo.backend_libreria.user.mapper.UserMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SaleMapper {

    private final UserMapper userMapper;
    private final PersonRepository personRepository;

    public Sale toEntity(SaleCreateDTO dto, User user, List<SaleItem> items, LocalDateTime saleDate,
                         BigDecimal totalAmount, BigDecimal amountPaid, BigDecimal change) {
        return Sale.builder()
                .saleDate(saleDate)
                .user(user)
                .totalAmount(totalAmount)
                .amountPaid(amountPaid)
                .change(change)
                .items(items)
                .build();
    }

    public SaleResponseDTO toResponseDTO(Sale sale) {
        // Obtener el Person asociado al usuario (puede ser null si no existe)
        Person person = personRepository.findByUserId(sale.getUser().getId()).orElse(null);
        UserShortResponseDTO userShort = userMapper.toShortDto(sale.getUser(), person);

        List<SaleItemResponseDTO> itemResponses = sale.getItems() != null
                ? sale.getItems().stream().map(this::toItemResponseDTO).collect(Collectors.toList())
                : null;

        return SaleResponseDTO.builder()
                .id(sale.getId())
                .saleDate(sale.getSaleDate())
                .totalAmount(sale.getTotalAmount())
                .amountPaid(sale.getAmountPaid())
                .change(sale.getChange())
                .user(userShort)
                .items(itemResponses)
                .build();
    }

    public SaleItemResponseDTO toItemResponseDTO(SaleItem entity) {
        Product product = entity.getProduct();
        ProductShortResponseDTO productShort = ProductShortResponseDTO.builder()
                .id(product.getId())
                .title(product.getTitle())
                .build();

        return SaleItemResponseDTO.builder()
                .id(entity.getId())
                .product(productShort)
                .quantity(entity.getQuantity())
                .unitPrice(entity.getUnitPrice())
                .totalPrice(entity.getTotalPrice())
                .build();
    }
}
