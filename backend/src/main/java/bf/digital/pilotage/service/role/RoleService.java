package bf.digital.pilotage.service.role;

import bf.digital.pilotage.dto.request.AssignPermissionsRequest;
import bf.digital.pilotage.dto.request.AssignRoleRequest;
import bf.digital.pilotage.dto.response.RoleResponse;

import java.util.List;

public interface RoleService {
    List<RoleResponse> getAllRoles();
    void assignRoleToUser(Long userId, AssignRoleRequest request);
    RoleResponse assignPermissionsToRole(Long roleId, AssignPermissionsRequest request);
}
