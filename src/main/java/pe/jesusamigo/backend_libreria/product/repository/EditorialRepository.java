package pe.jesusamigo.backend_libreria.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.jesusamigo.backend_libreria.product.entity.Editorial;

@Repository
public interface EditorialRepository extends JpaRepository<Editorial, Integer> {
    boolean existsByName(String name);
}
