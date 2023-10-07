package com.delivery.app.repositories;

import com.delivery.app.models.Person;
import com.delivery.app.models.Role;
import com.delivery.app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByPerson(Person person);
    Boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
    Boolean existsByEmail(String email);

    List<User> findByRole(Role role);

}
