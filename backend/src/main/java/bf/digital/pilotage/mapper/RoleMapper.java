package bf.digital.pilotage.mapper;

import bf.digital.pilotage.dto.response.RoleResponse;
import bf.digital.pilotage.entity.Permission;
import bf.digital.pilotage.entity.Role;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RoleMapper {

    public RoleResponse toResponse(Role role) {
        return RoleResponse.builder()
                .id(role.getId())
                .nom(role.getNom())
                .description(role.getDescription())
                .permissions(role.getPermissions().stream()
                        .map(Permission::getNom)
                        .collect(Collectors.toSet()))
                .build();
    }
}
