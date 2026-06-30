package bf.digital.pilotage.repository;

import bf.digital.pilotage.entity.Plateau;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlateauRepository extends JpaRepository<Plateau, Long> {
    Optional<Plateau> findByNom(String nom);
    boolean existsByNom(String nom);
}
