package org.clinica.parcial.domain.dtos.res;

import lombok.Data;
import org.clinica.parcial.domain.entities.Speciality;

@Data
public class ScheduleAppointmentDTO {
    private Speciality speciality;
    private ScheduleDocDTO doctor;
}
