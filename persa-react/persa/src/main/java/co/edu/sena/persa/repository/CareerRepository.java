package co.edu.sena.persa.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.sena.persa.models.Career;

public interface CareerRepository extends JpaRepository <Career, Long>{}