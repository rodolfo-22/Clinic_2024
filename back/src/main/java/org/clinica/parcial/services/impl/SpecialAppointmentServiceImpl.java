package org.clinica.parcial.services.impl;

import org.clinica.parcial.domain.entities.SpecialAppointment;
import org.clinica.parcial.repositories.SpecialAppointmentRepository;
import org.clinica.parcial.services.SpecialAppointmentService;
import org.springframework.stereotype.Service;

@Service
public class SpecialAppointmentServiceImpl implements SpecialAppointmentService {
    private final SpecialAppointmentRepository specialAppointmentRepository;

    public SpecialAppointmentServiceImpl(SpecialAppointmentRepository specialAppointmentRepository) {
        this.specialAppointmentRepository = specialAppointmentRepository;
    }

    @Override
    public void save(SpecialAppointment specialAppointment) {
        specialAppointmentRepository.save(specialAppointment);
    }
}