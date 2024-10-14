package org.clinica.parcial.domain.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "history")
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String reason;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private User user;

    @Column(name = "timestamp")
    private Date timestamp;
}
