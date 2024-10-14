package org.clinica.parcial.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class HistoryReqDTO {
    @NotBlank
    private String reason;
    @NotBlank
    private String identifier;
}
