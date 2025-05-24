package pe.jesusamigo.backend_libreria.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.jesusamigo.backend_libreria.inventory.entity.StockMovement;

import java.util.List;

@Repository
public interface StockMovementRepository extends JpaRepository<StockMovement, Integer> {
    List<StockMovement> findByProductId(Integer productId);
}
