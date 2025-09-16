package com.scm.backend.repositories;

import com.scm.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
//@RepositoryRestResource(excerptProjection = UserProjection.class)
public interface UserRepo extends JpaRepository<User, String> {

    List<User> findByEmailContainingIgnoreCase(String email);

    Optional<User> findByEmail(String email);
}
