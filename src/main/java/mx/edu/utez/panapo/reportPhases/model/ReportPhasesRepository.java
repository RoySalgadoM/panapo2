package mx.edu.utez.panapo.reportPhases.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportPhasesRepository extends JpaRepository<ReportPhases,Long> {
    boolean existsById(long id);
    Optional<ReportPhases> findById(long id);
}
