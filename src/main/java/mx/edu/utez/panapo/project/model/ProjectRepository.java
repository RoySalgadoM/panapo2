package mx.edu.utez.panapo.project.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findByAcronym(String acronym);
     Optional<Project> findByName(String name);

    boolean existsById(long id);
}