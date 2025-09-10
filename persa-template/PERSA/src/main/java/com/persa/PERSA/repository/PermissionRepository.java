package com.persa.PERSA.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.persa.PERSA.models.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long>{}