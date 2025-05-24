package pe.jesusamigo.backend_libreria.persons.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.jesusamigo.backend_libreria.persons.admin.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
}
