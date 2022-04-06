package mx.edu.utez.panapo.reportPhases.model;

import jdk.nashorn.internal.runtime.options.Option;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportPhasesRepository extends JpaRepository<ReportPhases,Long> {
    boolean existsById(long id);
    Optional<ReportPhases> findById(long id);
}
