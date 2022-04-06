package mx.edu.utez.panapo.rol;


import mx.edu.utez.panapo.project.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByDescription(String description);
    boolean existsById(long id);
}
