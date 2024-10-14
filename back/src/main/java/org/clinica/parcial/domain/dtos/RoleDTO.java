package org.clinica.parcial.domain.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.clinica.parcial.domain.entities.Token;

@Data
@NoArgsConstructor
public class RoleDTO {

    private String role;

    public RoleDTO(Token token) {
        this.role = token.getContent();
    }

}
