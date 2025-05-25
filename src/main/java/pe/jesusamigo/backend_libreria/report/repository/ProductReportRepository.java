package pe.jesusamigo.backend_libreria.report.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.jesusamigo.backend_libreria.product.entity.Product;
import pe.jesusamigo.backend_libreria.report.dto.ProductLowStockReportDTO;
import pe.jesusamigo.backend_libreria.report.dto.ProductSalesReportDTO;
import pe.jesusamigo.backend_libreria.report.dto.SaleReportDTO;

import java.time.LocalDateTime;
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
    List<ProductSalesReportDTO> findTopSellingProducts(Pageable pageable);

    @Query("""
        SELECT new pe.jesusamigo.backend_libreria.report.dto.ProductSalesReportDTO(
            p.id, p.title, SUM(si.quantity)
        )
        FROM SaleItem si
        JOIN si.product p
        JOIN si.sale s
        WHERE s.saleDate >= :startDate AND s.saleDate <= :endDate
        GROUP BY p.id, p.title
        ORDER BY SUM(si.quantity) DESC
    """)
    List<ProductSalesReportDTO> findTopSellingProductsBetweenDates(
            LocalDateTime startDate, LocalDateTime endDate, Pageable pageable
    );

    @Query("""
        SELECT new pe.jesusamigo.backend_libreria.report.dto.ProductLowStockReportDTO(
            p.id, p.title, p.stock
        )
        FROM Product p
        WHERE p.stock <= :threshold
        ORDER BY p.stock ASC
    """)
    List<ProductLowStockReportDTO> findProductsWithLowStock(int threshold);

    @Query("""
        SELECT new pe.jesusamigo.backend_libreria.report.dto.SaleReportDTO(
            s.id, s.saleDate, u.username, p.id, p.title, si.quantity, si.totalPrice
        )
        FROM SaleItem si
        JOIN si.sale s
        JOIN s.user u
        JOIN si.product p
        WHERE s.saleDate >= :startDate AND s.saleDate <= :endDate
          AND (:productId IS NULL OR p.id = :productId)
          AND (:userId IS NULL OR u.id = :userId)
        ORDER BY s.saleDate DESC
    """)
    List<SaleReportDTO> findSalesReportWithAllFilters(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("productId") Integer productId,
            @Param("userId") Integer userId,
            Pageable pageable
    );

    @Query("""
        SELECT new pe.jesusamigo.backend_libreria.report.dto.SaleReportDTO(
            s.id, s.saleDate, u.username, p.id, p.title, si.quantity, si.totalPrice
        )
        FROM SaleItem si
        JOIN si.sale s
        JOIN s.user u
        JOIN si.product p
        WHERE s.saleDate >= :startDate AND s.saleDate <= :endDate
        ORDER BY s.saleDate DESC
    """)
    List<SaleReportDTO> findSalesReportByDate(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable
    );

    @Query("""
        SELECT new pe.jesusamigo.backend_libreria.report.dto.SaleReportDTO(
            s.id, s.saleDate, u.username, p.id, p.title, si.quantity, si.totalPrice
        )
        FROM SaleItem si
        JOIN si.sale s
        JOIN s.user u
        JOIN si.product p
        WHERE p.id = :productId
        ORDER BY s.saleDate DESC
    """)
    List<SaleReportDTO> findSalesReportByProduct(
            @Param("productId") Integer productId,
            Pageable pageable
    );

    @Query("""
        SELECT new pe.jesusamigo.backend_libreria.report.dto.SaleReportDTO(
            s.id, s.saleDate, u.username, p.id, p.title, si.quantity, si.totalPrice
        )
        FROM SaleItem si
        JOIN si.sale s
        JOIN s.user u
        JOIN si.product p
        WHERE u.id = :userId
        ORDER BY s.saleDate DESC
    """)
    List<SaleReportDTO> findSalesReportByUser(
            @Param("userId") Integer userId,
            Pageable pageable
    );

    @Query("""
        SELECT new pe.jesusamigo.backend_libreria.report.dto.SaleReportDTO(
            s.id, s.saleDate, u.username, p.id, p.title, si.quantity, si.totalPrice
        )
        FROM SaleItem si
        JOIN si.sale s
        JOIN s.user u
        JOIN si.product p
        ORDER BY s.saleDate DESC
    """)
    List<SaleReportDTO> findAllSalesReport(Pageable pageable);
}
