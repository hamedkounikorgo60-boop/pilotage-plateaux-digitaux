package bf.digital.pilotage.service.impl;

import bf.digital.pilotage.dto.request.AssignPermissionsRequest;
import bf.digital.pilotage.dto.request.AssignRoleRequest;
import bf.digital.pilotage.dto.response.RoleResponse;
import bf.digital.pilotage.entity.Permission;
import bf.digital.pilotage.entity.Role;
import bf.digital.pilotage.entity.Utilisateur;
import bf.digital.pilotage.exception.BadRequestException;
import bf.digital.pilotage.mapper.RoleMapper;
import bf.digital.pilotage.repository.PermissionRepository;
import bf.digital.pilotage.repository.RoleRepository;
import bf.digital.pilotage.repository.UtilisateurRepository;
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
    private final UtilisateurRepository utilisateurRepository;
    private final RoleMapper roleMapper;

    @Override
    public List<RoleResponse> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(roleMapper::toResponse)
                .toList();
    }

    @Override
    public void assignRoleToUser(Long userId, AssignRoleRequest request) {
        Utilisateur utilisateur = utilisateurRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException("Utilisateur introuvable avec l'id : " + userId));

        Role role = roleRepository.findByNom(request.getRole())
                .orElseThrow(() -> new BadRequestException("Rôle inconnu : " + request.getRole()));

        Set<Role> roles = new HashSet<>();
        roles.add(role);
        utilisateur.setRoles(roles);

        utilisateurRepository.save(utilisateur);
    }

    @Override
    public RoleResponse assignPermissionsToRole(Long roleId, AssignPermissionsRequest request) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new BadRequestException("Rôle introuvable avec l'id : " + roleId));

        Set<Permission> permissions = new HashSet<>();
        for (String nomPermission : request.getPermissions()) {
            Permission permission = permissionRepository.findByNom(nomPermission)
                    .orElseThrow(() -> new BadRequestException("Permission inconnue : " + nomPermission));
            permissions.add(permission);
        }

        role.setPermissions(permissions);
        return roleMapper.toResponse(roleRepository.save(role));
    }
}
