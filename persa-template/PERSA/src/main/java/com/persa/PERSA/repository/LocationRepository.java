package com.persa.PERSA.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.persa.PERSA.models.Location;

public interface LocationRepository extends JpaRepository<Location, Long>{}