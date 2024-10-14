package org.clinica.parcial.repositories;

import org.clinica.parcial.domain.dtos.res.PrescriptionDTO;
import org.clinica.parcial.domain.entities.Prescription;
import org.clinica.parcial.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PrescriptionRepository  extends JpaRepository<Prescription, UUID> {
    List<Prescription> findByAppointmentUser(User user);
}
