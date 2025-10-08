package com.persa.PERSA.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.persa.PERSA.models.Role;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}