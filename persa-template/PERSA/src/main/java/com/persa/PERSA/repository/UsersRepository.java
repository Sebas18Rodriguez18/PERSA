package com.persa.PERSA.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.persa.PERSA.models.User;

public interface UsersRepository extends JpaRepository<User, Long> {}