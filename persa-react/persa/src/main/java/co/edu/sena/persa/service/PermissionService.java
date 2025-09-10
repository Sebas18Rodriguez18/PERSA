package co.edu.sena.persa.service;

import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.sena.persa.models.Permission;
import co.edu.sena.persa.models.User;
import co.edu.sena.persa.models.Career;
import co.edu.sena.persa.models.Location;
import co.edu.sena.persa.models.PermissionType;
import co.edu.sena.persa.repository.PermissionRepository;
import co.edu.sena.persa.repository.UsersRepository;
import co.edu.sena.persa.repository.CareerRepository;
import co.edu.sena.persa.repository.LocationRepository;
import co.edu.sena.persa.repository.PermissionTypeRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository permissionRepository;
    private final UsersRepository userRepository;
    private final CareerRepository careerRepository;
    private final LocationRepository locationRepository;
    private final PermissionTypeRepository permissionTypeRepository;

    public List<Permission> showAll() {
        return permissionRepository.findAll();
    }

    public Permission showById(Long id) {
        return permissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permiso no encontrado"));
    }

    public Permission save(Permission permission, Long instructorId, Long apprenticeId,
                           Long guardId, Long careerId, Long locationId, Long permissionTypeId) {

        User instructor = userRepository.findById(instructorId)
                .orElseThrow(() -> new RuntimeException("Instructor no encontrado"));
        User apprentice = userRepository.findById(apprenticeId)
                .orElseThrow(() -> new RuntimeException("Aprendiz no encontrado"));
        User guard = userRepository.findById(guardId)
                .orElseThrow(() -> new RuntimeException("Guardia no encontrado"));

        Career career = careerRepository.findById(careerId)
                .orElseThrow(() -> new RuntimeException("Carrera no encontrada"));

        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new RuntimeException("Sede no encontrada"));

        PermissionType permissionType = permissionTypeRepository.findById(permissionTypeId)
                .orElseThrow(() -> new RuntimeException("Tipo de permiso no encontrado"));

        permission.setInstructor(instructor);
        permission.setApprentice(apprentice);
        permission.setGuard(guard);
        permission.setCareer(career);
        permission.setLocation(location);
        permission.setPermissionType(permissionType);

        return permissionRepository.save(permission);
    }

    public void delete(Long id) {
        permissionRepository.deleteById(id);
    }
}