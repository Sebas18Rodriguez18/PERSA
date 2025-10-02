package co.edu.sena.persa.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.sena.persa.models.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long>{}