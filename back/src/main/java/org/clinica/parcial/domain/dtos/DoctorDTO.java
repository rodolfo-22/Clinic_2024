package org.clinica.parcial.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDTO {
    private UUID id;
    private String username;
    private boolean isAvailable;


}
