package bf.digital.pilotage.controller;

import bf.digital.pilotage.dto.request.RoleRequest;
import bf.digital.pilotage.entity.Role;
import bf.digital.pilotage.service.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(roleService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getRole(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Role> create(@RequestBody RoleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(roleService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Role> update(
            @PathVariable Long id,
            @RequestBody RoleRequest request) {

        return ResponseEntity.ok(roleService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        roleService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
