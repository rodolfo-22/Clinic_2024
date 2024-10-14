package org.clinica.parcial.domain.dtos.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.clinica.parcial.domain.entities.History;
import org.clinica.parcial.domain.entities.MedicalAppointment;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDoctorDTO {
    private List<History> historics;
    private List<ScheduleAppointmentDTO> doctors;
    private MedicalAppointment appointment;
}
