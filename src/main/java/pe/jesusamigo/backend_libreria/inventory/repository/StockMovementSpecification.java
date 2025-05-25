package pe.jesusamigo.backend_libreria.inventory.repository;

import org.springframework.data.jpa.domain.Specification;
import pe.jesusamigo.backend_libreria.inventory.entity.StockMovement;

import java.time.LocalDateTime;

public class StockMovementSpecification {

    public static Specification<StockMovement> hasProductId(Integer productId) {
        return (root, query, cb) -> productId == null ? null : cb.equal(root.get("product").get("id"), productId);
    }

    public static Specification<StockMovement> hasMovementType(StockMovement.MovementType type) {
        return (root, query, cb) -> type == null ? null : cb.equal(root.get("type"), type);
    }

    public static Specification<StockMovement> dateAfterOrEqual(LocalDateTime from) {
        return (root, query, cb) -> from == null ? null : cb.greaterThanOrEqualTo(root.get("movementDate"), from);
    }

    public static Specification<StockMovement> dateBeforeOrEqual(LocalDateTime to) {
        return (root, query, cb) -> to == null ? null : cb.lessThanOrEqualTo(root.get("movementDate"), to);
    }
}
