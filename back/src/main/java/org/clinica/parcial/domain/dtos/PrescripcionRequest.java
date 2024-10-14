package org.clinica.parcial.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrescripcionRequest {
    @NotBlank
    private String medicine;
    @NotBlank
    private String dose;
    @NotBlank
    private Date endDate;

}
