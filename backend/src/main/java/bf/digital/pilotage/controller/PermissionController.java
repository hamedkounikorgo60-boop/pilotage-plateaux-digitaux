package bf.digital.pilotage.controller;

import bf.digital.pilotage.entity.Permission;
import bf.digital.pilotage.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionRepository permissionRepository;

    @GetMapping
    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }

    @GetMapping("/{id}")
    public Permission getPermissionById(@PathVariable Long id) {
        return permissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permission introuvable"));
    }
}
