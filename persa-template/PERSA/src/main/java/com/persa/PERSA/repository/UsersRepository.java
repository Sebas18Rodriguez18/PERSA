package com.persa.PERSA.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.persa.PERSA.models.User;

public interface UsersRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.role.id = :roleId")
    List<User> findByRoleId(@Param("roleId") Long roleId);

    @Query("SELECT u FROM User u WHERE u.course.id = :courseId AND u.role.id = 3")
    List<User> findApprenticesByCourseId(@Param("courseId") Long courseId);

    List<User> findByRole_Name(String roleName);

    List<User> findByRole_NameIgnoreCase(String roleName);
}