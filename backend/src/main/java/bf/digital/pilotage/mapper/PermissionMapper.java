package bf.digital.pilotage.mapper;

import bf.digital.pilotage.dto.response.PermissionResponse;
import bf.digital.pilotage.entity.Permission;
import org.springframework.stereotype.Component;

@Component
public class PermissionMapper {

    public PermissionResponse toResponse(Permission permission) {
        return PermissionResponse.builder()
                .id(permission.getId())
                .nom(permission.getNom())
                .description(permission.getDescription())
                .build();
    }
}
