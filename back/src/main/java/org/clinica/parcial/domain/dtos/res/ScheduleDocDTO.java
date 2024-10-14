package org.clinica.parcial.domain.dtos.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDocDTO {
    private UUID id;
    private String name;
    private String email;
}
