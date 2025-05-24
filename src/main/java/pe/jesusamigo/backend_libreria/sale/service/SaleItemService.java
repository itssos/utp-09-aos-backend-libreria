package pe.jesusamigo.backend_libreria.sale.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.jesusamigo.backend_libreria.sale.entity.SaleItem;
import pe.jesusamigo.backend_libreria.sale.repository.SaleItemRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SaleItemService {

    private final SaleItemRepository saleItemRepository;

    public List<SaleItem> findBySaleId(Integer saleId) {
        return saleItemRepository.findBySaleId(saleId);
    }

    public SaleItem findById(Integer id) {
        return saleItemRepository.findById(id).orElse(null);
    }
}
