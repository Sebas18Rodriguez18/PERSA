package co.edu.sena.persa.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.sena.persa.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{}