package bf.digital.pilotage.service.impl;

import bf.digital.pilotage.dto.request.RoleRequest;
import bf.digital.pilotage.entity.Permission;
import bf.digital.pilotage.entity.Role;
import bf.digital.pilotage.exception.BadRequestException;
import bf.digital.pilotage.repository.PermissionRepository;
import bf.digital.pilotage.repository.RoleRepository;
import bf.digital.pilotage.service.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Rôle introuvable."));
    }

    @Override
    public Role create(RoleRequest request) {

        if (roleRepository.existsByNom(request.getNom())) {
            throw new BadRequestException("Ce rôle existe déjà.");
        }

        Set<Permission> permissions = new HashSet<>();

        if (request.getPermissionIds() != null) {
            request.getPermissionIds().forEach(id ->
                    permissions.add(
                            permissionRepository.findById(id)
                                    .orElseThrow(() ->
                                            new BadRequestException("Permission introuvable : " + id))
                    )
            );
        }

        Role role = Role.builder()
                .nom(request.getNom())
                .description(request.getDescription())
                .permissions(permissions)
                .build();

        return roleRepository.save(role);
    }

    @Override
    public Role update(Long id, RoleRequest request) {

        Role role = findById(id);

        role.setNom(request.getNom());
        role.setDescription(request.getDescription());

        Set<Permission> permissions = new HashSet<>();

        if (request.getPermissionIds() != null) {
            request.getPermissionIds().forEach(pid ->
                    permissions.add(
                            permissionRepository.findById(pid)
                                    .orElseThrow(() ->
                                            new BadRequestException("Permission introuvable : " + pid))
                    )
            );
        }

        role.setPermissions(permissions);

        return roleRepository.save(role);
    }

    @Override
    public void delete(Long id) {
        roleRepository.delete(findById(id));
    }
}
