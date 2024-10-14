package org.clinica.parcial.services;

import org.clinica.parcial.domain.dtos.MedicalAppointmentDTO;
import org.clinica.parcial.domain.entities.MedicalAppointment;
import org.clinica.parcial.domain.dtos.RequestMedicalAppointmentDTO;
import org.clinica.parcial.domain.entities.User;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface AppointmentService {
    MedicalAppointmentDTO createAppointment(RequestMedicalAppointmentDTO appointmentDTO, User user);
    List<MedicalAppointmentDTO> getAppointmentsByUser(User user);
    List<MedicalAppointmentDTO> getAppointmentsByUserAndState(User user, String state);
    void saveAppointment(MedicalAppointment appointment);
    MedicalAppointment findById(UUID appointmentId);

    void appointmentFinish(MedicalAppointment appointment);

    void cancelAppointment(UUID appointment);

    List<MedicalAppointment> findAllAppointments(); // Esto debe estar aquí

    List<MedicalAppointment> getAppointmentsByDoctorAndDate(User doctor, Date date);

    // Agregar el método toDTO
    MedicalAppointmentDTO toDTO(MedicalAppointment appointment);

}
