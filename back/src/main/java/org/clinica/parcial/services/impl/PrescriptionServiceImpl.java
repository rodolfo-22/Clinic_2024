package org.clinica.parcial.services.impl;

import jakarta.persistence.EntityNotFoundException;
import org.clinica.parcial.domain.dtos.ManyPrescriptionRequest;
import org.clinica.parcial.domain.dtos.res.PrescriptionDTO;
import org.clinica.parcial.domain.entities.MedicalAppointment;
import org.clinica.parcial.domain.entities.Prescription;
import org.clinica.parcial.domain.entities.User;
import org.clinica.parcial.repositories.PrescriptionRepository;
import org.clinica.parcial.repositories.UserRepository;
import org.clinica.parcial.services.AppointmentService;
import org.clinica.parcial.services.PrescriptionService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {
    final PrescriptionRepository prescriptionRepository;
    final UserRepository userRepository;

    final AppointmentService appointmentService;

    public PrescriptionServiceImpl(PrescriptionRepository prescriptionRepository, UserRepository userRepository, AppointmentService appointmentService) {
        this.prescriptionRepository = prescriptionRepository;
        this.userRepository = userRepository;
        this.appointmentService = appointmentService;
    }

    @Override
    public void createPrescription(MedicalAppointment appointment, ManyPrescriptionRequest req) {
        List<Prescription> _prescriptions = new ArrayList<>();
        req.getPrescriptions().forEach(prescription -> {
            Prescription newPrescription = new Prescription();
            newPrescription.setAppointment(appointment);
            newPrescription.setMedicine(prescription.getMedicine());
            newPrescription.setDose(prescription.getDose());
            newPrescription.setEndDate(prescription.getEndDate());

            Prescription createdPrescription = prescriptionRepository.save(newPrescription);
            _prescriptions.add(createdPrescription);
        });

        appointment.setPrescription(_prescriptions);
    }

    @Override
    public List<PrescriptionDTO> findPrescriptionByUser(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        List<Prescription> prescriptions = prescriptionRepository.findByAppointmentUser(user);
        return prescriptions.stream()
                .map(prescription -> new PrescriptionDTO(
                        prescription.getId(),
                        prescription.getMedicine(),
                        prescription.getDose(),
                        prescription.getEndDate()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Prescription> findPrescriptionsForAuthenticatedUser(User user) {
        return prescriptionRepository.findByAppointmentUser(user).stream()
                .filter(prescription -> Date.from(Instant.now()).before(prescription.getEndDate()))
                .collect(Collectors.toList());
    }
}
