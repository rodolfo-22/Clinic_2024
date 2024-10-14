package org.clinica.parcial.repositories;

import org.clinica.parcial.domain.entities.SpecialAppointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpecialAppointmentRepository extends JpaRepository<SpecialAppointment, UUID> {
}
