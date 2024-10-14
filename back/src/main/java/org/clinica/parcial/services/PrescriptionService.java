package org.clinica.parcial.services;

import org.clinica.parcial.domain.dtos.ManyPrescriptionRequest;
import org.clinica.parcial.domain.dtos.res.PrescriptionDTO;
import org.clinica.parcial.domain.entities.MedicalAppointment;
import org.clinica.parcial.domain.entities.Prescription;
import org.clinica.parcial.domain.entities.User;

import java.util.List;
import java.util.UUID;

public interface PrescriptionService {
    void createPrescription(MedicalAppointment appointment, ManyPrescriptionRequest req);
    List<PrescriptionDTO> findPrescriptionByUser(UUID user);
    List<Prescription> findPrescriptionsForAuthenticatedUser(User user);

}
