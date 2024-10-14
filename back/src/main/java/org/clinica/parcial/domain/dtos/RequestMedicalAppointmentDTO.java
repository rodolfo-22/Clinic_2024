package org.clinica.parcial.domain.dtos;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class RequestMedicalAppointmentDTO {
    private Date startTimestamp;
    private Date endTimestamp;
    private String userId;
    private String reason;
    private List<String> specialties;

    private String userName; // Nuevo campo para el nombre del usuario
    private String userEmail; // Nuevo campo para el email del usuario

    private Integer duration; // Añadido campo para la duración

}
