package pe.jesusamigo.backend_libreria.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.jesusamigo.backend_libreria.product.entity.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
    boolean existsByName(String name);
}
