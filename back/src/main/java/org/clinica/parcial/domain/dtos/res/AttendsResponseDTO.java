package org.clinica.parcial.domain.dtos.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.clinica.parcial.domain.entities.Prescription;
import org.clinica.parcial.domain.entities.User;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendsResponseDTO {
    private User principalDoc;
    private AppointmentDoctorDTO appointments;
    private List<Prescription> prescriptions;

}
