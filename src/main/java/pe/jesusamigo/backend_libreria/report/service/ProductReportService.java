package pe.jesusamigo.backend_libreria.report.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pe.jesusamigo.backend_libreria.report.dto.ProductLowStockReportDTO;
import pe.jesusamigo.backend_libreria.report.dto.ProductSalesReportDTO;
import pe.jesusamigo.backend_libreria.report.dto.SaleReportDTO;
import pe.jesusamigo.backend_libreria.report.repository.ProductReportRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductReportService {

    private final ProductReportRepository productReportRepository;

    public List<ProductSalesReportDTO> getTopSellingProducts(int limit, LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate != null && endDate != null) {
            return productReportRepository.findTopSellingProductsBetweenDates(startDate, endDate, PageRequest.of(0, limit));
        } else {
            return productReportRepository.findTopSellingProducts(PageRequest.of(0, limit));
        }
    }

    public List<SaleReportDTO> getSalesReport(
            LocalDateTime startDate,
            LocalDateTime endDate,
            Integer productId,
            Integer userId,
            int limit
    ) {
        if (startDate != null && endDate != null && productId != null && userId != null) {
            return productReportRepository.findSalesReportWithAllFilters(startDate, endDate, productId, userId, PageRequest.of(0, limit));
        } else if (startDate != null && endDate != null && productId == null && userId == null) {
            return productReportRepository.findSalesReportByDate(startDate, endDate, PageRequest.of(0, limit));
        } else if (productId != null && startDate == null && userId == null) {
            return productReportRepository.findSalesReportByProduct(productId, PageRequest.of(0, limit));
        } else if (userId != null && startDate == null && productId == null) {
            return productReportRepository.findSalesReportByUser(userId, PageRequest.of(0, limit));
        } else if (startDate == null && endDate == null && productId == null && userId == null) {
            return productReportRepository.findAllSalesReport(PageRequest.of(0, limit));
        } else if (startDate != null && endDate != null && productId != null && userId == null) {
            // fecha + producto
            return productReportRepository.findSalesReportWithAllFilters(startDate, endDate, productId, null, PageRequest.of(0, limit));
        } else if (startDate != null && endDate != null && productId == null && userId != null) {
            // fecha + usuario
            return productReportRepository.findSalesReportWithAllFilters(startDate, endDate, null, userId, PageRequest.of(0, limit));
        } else if (productId != null && userId != null && startDate == null) {
            // producto + usuario
            return productReportRepository.findSalesReportWithAllFilters(null, null, productId, userId, PageRequest.of(0, limit));
        } else if (startDate != null && endDate != null && productId != null && userId == null) {
            // fecha + producto
            return productReportRepository.findSalesReportWithAllFilters(startDate, endDate, productId, null, PageRequest.of(0, limit));
        } else if (startDate != null && endDate != null && userId != null && productId == null) {
            // fecha + usuario
            return productReportRepository.findSalesReportWithAllFilters(startDate, endDate, null, userId, PageRequest.of(0, limit));
        } else {
            // Si no entra en ninguna, retorna vacío
            return List.of();
        }
    }

    public List<ProductLowStockReportDTO> getProductsWithLowStock(int threshold) {
        return productReportRepository.findProductsWithLowStock(threshold);
    }
}
