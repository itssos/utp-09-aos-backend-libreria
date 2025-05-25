package pe.jesusamigo.backend_libreria.report.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.jesusamigo.backend_libreria.product.entity.Product;
import pe.jesusamigo.backend_libreria.report.dto.ProductSalesReportDTO;

import java.util.List;

public interface ProductReportRepository extends JpaRepository<Product, Integer> {

    @Query("""
        SELECT new pe.jesusamigo.backend_libreria.report.dto.ProductSalesReportDTO(
            p.id, p.title, SUM(si.quantity)
        )
        FROM SaleItem si
        JOIN si.product p
        GROUP BY p.id, p.title
        ORDER BY SUM(si.quantity) DESC
    """)
    List<ProductSalesReportDTO> findTopSellingProducts();
}
