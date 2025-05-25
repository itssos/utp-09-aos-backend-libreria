package pe.jesusamigo.backend_libreria.sale.repository;

import org.springframework.data.jpa.domain.Specification;
import pe.jesusamigo.backend_libreria.sale.entity.Sale;

import java.time.LocalDateTime;

public class SaleSpecification {

    public static Specification<Sale> hasUserId(Integer userId) {
        return (root, query, cb) -> userId == null ? null : cb.equal(root.get("user").get("id"), userId);
    }

    public static Specification<Sale> dateAfterOrEqual(LocalDateTime start) {
        return (root, query, cb) -> start == null ? null : cb.greaterThanOrEqualTo(root.get("saleDate"), start);
    }

    public static Specification<Sale> dateBeforeOrEqual(LocalDateTime end) {
        return (root, query, cb) -> end == null ? null : cb.lessThanOrEqualTo(root.get("saleDate"), end);
    }
}
