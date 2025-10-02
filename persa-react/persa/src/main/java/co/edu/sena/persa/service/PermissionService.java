package co.edu.sena.persa.service;

import co.edu.sena.persa.models.Permission;
import co.edu.sena.persa.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository permissionRepository;

    public List<Permission> findAll() {
        return permissionRepository.findAll();
    }

    public Optional<Permission> findById(Long id) {
        return permissionRepository.findById(id);
    }

    public Permission save(Permission permission) {
        return permissionRepository.save(permission);
    }

    public Permission update(Long id, Permission permission) {
        return permissionRepository.findById(id)
                .map(existing -> {
                    existing.setPermissionDate(permission.getPermissionDate());
                    existing.setStartTime(permission.getStartTime());
                    existing.setEndTime(permission.getEndTime());
                    existing.setStatus(permission.getStatus());
                    existing.setInstructor(permission.getInstructor());
                    existing.setApprentice(permission.getApprentice());
                    existing.setGuard(permission.getGuard());
                    existing.setLocation(permission.getLocation());
                    existing.setPermissionType(permission.getPermissionType());
                    existing.setCareer(permission.getCareer());
                    return permissionRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Permission not found with id " + id));
    }

    public void delete(Long id) {
        permissionRepository.deleteById(id);
    }
}