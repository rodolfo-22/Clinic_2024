package org.clinica.parcial.domain.dtos.res;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
public class PrescriptionDTO {
    private UUID id;
    private String medicine;
    private String dose;
    private Date endDate;
}
