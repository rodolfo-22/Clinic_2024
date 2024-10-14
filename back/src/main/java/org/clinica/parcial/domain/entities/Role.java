package org.clinica.parcial.domain.entities;

import org.springframework.security.core.GrantedAuthority;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "role")
public class Role implements GrantedAuthority {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles", cascade = CascadeType.ALL)
    @JsonIgnore
    List<User> users;

    @Override
    public String getAuthority() {
        return name;
    }
}