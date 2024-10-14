package org.clinica.parcial.domain.dtos;

import org.clinica.parcial.domain.entities.Role;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserDTO {
    private UUID id;
    private String username;
    private String email;
    private List<String> roles;

    public UserDTO(UUID id, String username, String email, List<Role> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles.stream().map(Role::getAuthority).collect(Collectors.toList());
    }

    // Getters y setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
