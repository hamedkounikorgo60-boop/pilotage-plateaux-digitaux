package bf.digital.pilotage.controller;

import bf.digital.pilotage.dto.request.AssignPermissionsRequest;
import bf.digital.pilotage.dto.request.AssignRoleRequest;
import bf.digital.pilotage.dto.response.RoleResponse;
import bf.digital.pilotage.service.role.RoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Rôles", description = "Gestion des rôles")
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/api/roles")
    @PreAuthorize("hasAuthority('USER_READ')")
    public ResponseEntity<List<RoleResponse>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @PutMapping("/api/users/{id}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> assignRoleToUser(@PathVariable Long id,
                                                   @Valid @RequestBody AssignRoleRequest request) {
        roleService.assignRoleToUser(id, request);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/api/roles/{id}/permissions")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoleResponse> assignPermissionsToRole(@PathVariable Long id,
                                                                   @Valid @RequestBody AssignPermissionsRequest request) {
        return ResponseEntity.ok(roleService.assignPermissionsToRole(id, request));
    }
}
