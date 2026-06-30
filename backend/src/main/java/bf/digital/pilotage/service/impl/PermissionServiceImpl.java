package bf.digital.pilotage.service.impl;

import bf.digital.pilotage.dto.response.PermissionResponse;
import bf.digital.pilotage.mapper.PermissionMapper;
import bf.digital.pilotage.repository.PermissionRepository;
import bf.digital.pilotage.service.permission.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;

    @Override
    public List<PermissionResponse> getAllPermissions() {
        return permissionRepository.findAll().stream()
                .map(permissionMapper::toResponse)
                .toList();
    }
}
