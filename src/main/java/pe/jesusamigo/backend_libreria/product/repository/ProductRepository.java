package pe.jesusamigo.backend_libreria.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.jesusamigo.backend_libreria.product.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    boolean existsByTitle(String title);
}
