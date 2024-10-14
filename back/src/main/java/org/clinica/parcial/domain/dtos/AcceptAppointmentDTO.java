package org.clinica.parcial.domain.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class AcceptAppointmentDTO {
    @NotNull
    private UUID appointmentId;

    @NotNull
    private Boolean isAccepted;

    @NotNull
    private Date fechaRealizacion;

    @NotNull
    private Integer duration;

    @NotNull
    private List<UUID> doctorsId;

    @NotNull
    private List<String> specialties;

    private String userId;
}
