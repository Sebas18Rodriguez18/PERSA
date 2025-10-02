package co.edu.sena.persa.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.sena.persa.models.User;

public interface UsersRepository extends JpaRepository<User, Long> {}