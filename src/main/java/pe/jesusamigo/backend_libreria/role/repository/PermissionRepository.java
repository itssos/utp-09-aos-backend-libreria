package pe.jesusamigo.backend_libreria.role.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.jesusamigo.backend_libreria.role.entity.Permission;

import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {
    Optional<Permission> findByName(String name);
}
