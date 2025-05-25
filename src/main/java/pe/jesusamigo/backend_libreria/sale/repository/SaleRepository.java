package pe.jesusamigo.backend_libreria.sale.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pe.jesusamigo.backend_libreria.sale.entity.Sale;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Integer>, JpaSpecificationExecutor<Sale> {
    List<Sale> findByUserId(Integer userId);
    List<Sale> findBySaleDateBetween(LocalDateTime start, LocalDateTime end);
}
