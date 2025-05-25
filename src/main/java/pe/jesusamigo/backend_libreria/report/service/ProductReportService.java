package pe.jesusamigo.backend_libreria.report.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.jesusamigo.backend_libreria.report.dto.ProductSalesReportDTO;
import pe.jesusamigo.backend_libreria.report.repository.ProductReportRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductReportService {

    private final ProductReportRepository productReportRepository;

    public List<ProductSalesReportDTO> getTopSellingProducts() {
        return productReportRepository.findTopSellingProducts();
    }
}
