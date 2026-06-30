package bf.digital.pilotage.repository;

import bf.digital.pilotage.entity.Tache;
import bf.digital.pilotage.enums.StatutTache;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TacheRepository extends JpaRepository<Tache, Long> {
    List<Tache> findByPlateauId(Long plateauId);
    List<Tache> findByAssignesId(Long userId);
    List<Tache> findByPlateauIdAndStatut(Long plateauId, StatutTache statut);
    long countByPlateauId(Long plateauId);
    long countByPlateauIdAndStatut(Long plateauId, StatutTache statut);
}
