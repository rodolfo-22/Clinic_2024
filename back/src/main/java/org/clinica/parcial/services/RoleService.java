package org.clinica.parcial.services;

import org.clinica.parcial.domain.entities.Role;
import org.clinica.parcial.domain.entities.User;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<Role> findRolesByUsers(List<User> users);
    Optional<Role> findByName(String role);
}
