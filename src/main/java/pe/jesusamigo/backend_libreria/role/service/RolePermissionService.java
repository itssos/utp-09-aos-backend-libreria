package pe.jesusamigo.backend_libreria.role.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.jesusamigo.backend_libreria.exception.ResourceNotFoundException;
import pe.jesusamigo.backend_libreria.role.entity.Permission;
import pe.jesusamigo.backend_libreria.role.entity.Role;
import pe.jesusamigo.backend_libreria.role.repository.PermissionRepository;
import pe.jesusamigo.backend_libreria.role.repository.RoleRepository;

@Service
@RequiredArgsConstructor
public class RolePermissionService {
    private final RoleRepository roleRepo;
    private final PermissionRepository permRepo;

    @Transactional
    public Role addPermissionToRole(Integer roleId, String permName) {
        Role role = roleRepo.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontro el rol de ID: " + roleId));
        Permission p = permRepo.findByName(permName)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontro el permiso: " + permName));
        role.getPermissions().add(p);
        return roleRepo.save(role);
    }

    @Transactional
    public Role removePermissionFromRole(Integer roleId, String permName) {
        Role role = roleRepo.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontro el rol de ID: " + roleId));
        Permission p = permRepo.findByName(permName)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontro el permiso: " + permName));
        role.getPermissions().remove(p);
        return roleRepo.save(role);
    }
}