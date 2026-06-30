package bf.digital.pilotage.config;

import bf.digital.pilotage.entity.Permission;
import bf.digital.pilotage.entity.Role;
import bf.digital.pilotage.repository.PermissionRepository;
import bf.digital.pilotage.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    private static final List<String> PERMISSIONS = List.of(
            "USER_READ", "USER_CREATE", "USER_UPDATE", "USER_DELETE",
            "PLATEAU_READ", "PLATEAU_CREATE", "PLATEAU_UPDATE", "PLATEAU_DELETE",
            "TASK_READ", "TASK_CREATE", "TASK_UPDATE", "TASK_DELETE",
            "REPORT_READ"
    );

    @Override
    @Transactional
    public void run(String... args) {
        // 1. Créer les permissions si elles n'existent pas
        for (String nomPermission : PERMISSIONS) {
            if (!permissionRepository.existsByNom(nomPermission)) {
                permissionRepository.save(
                        Permission.builder().nom(nomPermission).build()
                );
            }
        }

        Set<Permission> toutesLesPermissions = new HashSet<>(permissionRepository.findAll());

        // 2. Créer le rôle ADMIN avec toutes les permissions
        if (!roleRepository.existsByNom("ADMIN")) {
            roleRepository.save(
                    Role.builder()
                            .nom("ADMIN")
                            .description("Administrateur du pôle digital")
                            .permissions(toutesLesPermissions)
                            .build()
            );
        }

        // 3. Créer le rôle CHEF_PLATEAU
        if (!roleRepository.existsByNom("CHEF_PLATEAU")) {
            Set<Permission> permissionsChef = filterPermissions(
                    "PLATEAU_READ", "TASK_READ", "TASK_CREATE", "TASK_UPDATE", "TASK_DELETE", "REPORT_READ"
            );
            roleRepository.save(
                    Role.builder()
                            .nom("CHEF_PLATEAU")
                            .description("Chef de plateau")
                            .permissions(permissionsChef)
                            .build()
            );
        }

        // 4. Créer le rôle MEMBRE
        if (!roleRepository.existsByNom("MEMBRE")) {
            Set<Permission> permissionsMembre = filterPermissions(
                    "TASK_READ", "TASK_UPDATE"
            );
            roleRepository.save(
                    Role.builder()
                            .nom("MEMBRE")
                            .description("Membre d'une équipe")
                            .permissions(permissionsMembre)
                            .build()
            );
        }
    }

    private Set<Permission> filterPermissions(String... noms) {
        Set<String> nomsSet = Set.of(noms);
        Set<Permission> result = new HashSet<>();
        permissionRepository.findAll().forEach(p -> {
            if (nomsSet.contains(p.getNom())) {
                result.add(p);
            }
        });
        return result;
    }
}