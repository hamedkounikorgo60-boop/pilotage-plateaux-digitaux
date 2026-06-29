package bf.digital.pilotage.repository;

import bf.digital.pilotage.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Optional<Permission> findByNom(String nom);

    boolean existsByNom(String nom);

}