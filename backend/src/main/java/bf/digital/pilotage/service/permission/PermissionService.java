package bf.digital.pilotage.service.permission;

import bf.digital.pilotage.dto.response.PermissionResponse;

import java.util.List;

public interface PermissionService {
    List<PermissionResponse> getAllPermissions();
}
