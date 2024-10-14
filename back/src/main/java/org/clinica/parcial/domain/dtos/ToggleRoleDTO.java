package org.clinica.parcial.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ToggleRoleDTO {
    @NotBlank
    private String identifier;

    @NotBlank
    private String role;

    // Getters y setters
    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
