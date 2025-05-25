package pe.jesusamigo.backend_libreria.product.repository;

import org.springframework.data.jpa.domain.Specification;
import pe.jesusamigo.backend_libreria.product.entity.Product;

import java.math.BigDecimal;

public class ProductSpecification {

    public static Specification<Product> hasCategory(Integer categoryId) {
        return (root, query, cb) -> categoryId == null ? null : cb.equal(root.get("category").get("id"), categoryId);
    }

    public static Specification<Product> hasEditorial(Integer editorialId) {
        return (root, query, cb) -> editorialId == null ? null : cb.equal(root.get("editorial").get("id"), editorialId);
    }

    public static Specification<Product> hasAuthor(Integer authorId) {
        return (root, query, cb) -> authorId == null ? null : cb.equal(root.get("author").get("id"), authorId);
    }

    public static Specification<Product> hasPriceBetween(BigDecimal min, BigDecimal max) {
        return (root, query, cb) -> {
            if (min != null && max != null) {
                return cb.between(root.get("price"), min, max);
            } else if (min != null) {
                return cb.greaterThanOrEqualTo(root.get("price"), min);
            } else if (max != null) {
                return cb.lessThanOrEqualTo(root.get("price"), max);
            } else {
                return null;
            }
        };
    }

    public static Specification<Product> titleContains(String fragment) {
        return (root, query, cb) -> fragment == null || fragment.isBlank()
                ? null
                : cb.like(cb.lower(root.get("title")), "%" + fragment.trim().toLowerCase() + "%");
    }
}
