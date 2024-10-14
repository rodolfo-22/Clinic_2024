package org.clinica.parcial.domain.dtos.res;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.clinica.parcial.domain.entities.Token;

@Data
@NoArgsConstructor
public class TokenDTO {

    private String token;
    private String role;

    public TokenDTO(Token token, String role) {
        this.token = token.getContent();
        this.role = role;
    }

    // Getters y setters
    public String getToken() {
        return token;
    }

    public String getRole() {
        return role;
    }
}
