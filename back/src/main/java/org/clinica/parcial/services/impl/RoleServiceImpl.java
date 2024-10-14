package org.clinica.parcial.services.impl;

import org.clinica.parcial.services.RoleService;
import org.clinica.parcial.domain.entities.Role;
import org.clinica.parcial.domain.entities.User;
import org.clinica.parcial.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> findRolesByUsers(List<User> users) {
        return roleRepository.findRolesByUsers(users);
    }

    @Override
    public Optional<Role> findByName(String role) {
        return roleRepository.findByName(role);
    }
}