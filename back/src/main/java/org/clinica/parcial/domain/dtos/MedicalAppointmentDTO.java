package org.clinica.parcial.domain.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class MedicalAppointmentDTO {
    private UUID id;
    private Date startTimestamp;
    private Date realizationDate;
    private String reason;
    private String userId;
    private String userName;
    private String userEmail;
    private String state;
    private Integer duration;
    private List<String> specialties;  // Añade otros campos necesarios
}

