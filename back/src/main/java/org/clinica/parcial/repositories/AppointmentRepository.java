package org.clinica.parcial.repositories;

import org.clinica.parcial.domain.entities.MedicalAppointment;
import org.clinica.parcial.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<MedicalAppointment, UUID> {
    List<MedicalAppointment> findByUser(User user);
    List<MedicalAppointment> findByUserAndState(User user, String state);

    @Query("SELECT a FROM MedicalAppointment a WHERE a.doctor = :doctor AND FUNCTION('DATE', a.realizacionDate) = :date")
    List<MedicalAppointment> findByDoctorAndRealizacionDate(@Param("doctor") User doctor, @Param("date") Date date);

    List<MedicalAppointment> findByDoctorAndRealizacionDateBetween(User doctor, Date start, Date end);

}
