package co.edu.sena.persa.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.sena.persa.models.Location;

public interface LocationRepository extends JpaRepository<Location, Long>{}