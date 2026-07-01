package bf.digital.pilotage.service.role;

import bf.digital.pilotage.dto.request.RoleRequest;
import bf.digital.pilotage.entity.Role;

import java.util.List;

public interface RoleService {

    List<Role> findAll();

    Role findById(Long id);

    Role create(RoleRequest request);

    Role update(Long id, RoleRequest request);

    void delete(Long id);

}
