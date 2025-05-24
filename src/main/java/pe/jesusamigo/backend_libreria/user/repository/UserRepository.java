package pe.jesusamigo.backend_libreria.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.jesusamigo.backend_libreria.user.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String newUsername);
}
