package org.clinica.parcial.repositories;

import org.clinica.parcial.domain.entities.Role;
import org.clinica.parcial.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    List<Role> findRolesByUsers(List<User> users);

    Optional<Role> findByName(String role);
}
