package org.clinica.parcial.services.impl;

import jakarta.persistence.EntityNotFoundException;
import org.clinica.parcial.domain.dtos.DoctorDTO;
import org.clinica.parcial.domain.dtos.MedicalAppointmentDTO;
import org.clinica.parcial.domain.dtos.RequestMedicalAppointmentDTO;
import org.clinica.parcial.domain.entities.MedicalAppointment;
import org.clinica.parcial.domain.entities.User;
import org.clinica.parcial.repositories.AppointmentRepository;
import org.clinica.parcial.services.AppointmentService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;


    public AppointmentServiceImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;

    }

    @Override
    public MedicalAppointmentDTO createAppointment(RequestMedicalAppointmentDTO appointmentDTO, User user) {
        MedicalAppointment appointment = new MedicalAppointment();
        appointment.setSolicitadaDate(new Date());
        appointment.setUser(user);
        appointment.setState("pending");
        appointment.setReason(appointmentDTO.getReason());
        appointment.setDuration(appointmentDTO.getDuration() != null ? appointmentDTO.getDuration() : 0); // Asegura que duration no sea nulo
        appointment = appointmentRepository.save(appointment);
        return toDTO(appointment);
    }

    @Override
    public List<MedicalAppointmentDTO> getAppointmentsByUser(User user) {
        return appointmentRepository.findByUser(user).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MedicalAppointmentDTO> getAppointmentsByUserAndState(User user, String state) {
        return appointmentRepository.findByUserAndState(user, state).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void saveAppointment(MedicalAppointment appointment) {
        appointmentRepository.save(appointment);
    }

    @Override
    public MedicalAppointment findById(UUID appointmentId) {
        return appointmentRepository.findById(appointmentId).orElse(null);
    }

    @Override
    public void appointmentFinish(MedicalAppointment appointment) {
        appointment.setState("finished");
        appointment.setFinalizationDate(Date.from(Instant.now()));
        appointmentRepository.save(appointment);
    }

    @Override
    public void cancelAppointment(UUID appointmentId) {
        MedicalAppointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found"));
        appointment.setState("cancelled");
        appointmentRepository.save(appointment);
    }

    @Override
    public List<MedicalAppointment> findAllAppointments() {

        return appointmentRepository.findAll();
    }

    @Override
    public List<MedicalAppointment> getAppointmentsByDoctorAndDate(User doctor, Date date) {
        Calendar start = Calendar.getInstance();
        start.setTime(date);
        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        start.set(Calendar.MILLISECOND, 0);

        Calendar end = Calendar.getInstance();
        end.setTime(date);
        end.set(Calendar.HOUR_OF_DAY, 23);
        end.set(Calendar.MINUTE, 59);
        end.set(Calendar.SECOND, 59);
        end.set(Calendar.MILLISECOND, 999);

        System.out.println("Fetching appointments for doctor: " + doctor.getId() + " between: " + start.getTime() + " and " + end.getTime());
        return appointmentRepository.findByDoctorAndRealizacionDateBetween(doctor, start.getTime(), end.getTime());
    }

    public MedicalAppointmentDTO toDTO(MedicalAppointment appointment) {
        MedicalAppointmentDTO dto = new MedicalAppointmentDTO();
        dto.setId(appointment.getId());
        dto.setStartTimestamp(appointment.getSolicitadaDate());
        dto.setRealizationDate(appointment.getRealizacionDate());
        dto.setReason(appointment.getReason());
        dto.setState(appointment.getState());
        dto.setDuration(appointment.getDuration());
        if (appointment.getUser() != null) {
            dto.setUserId(appointment.getUser().getId().toString());
            dto.setUserName(appointment.getUser().getUsername());
            dto.setUserEmail(appointment.getUser().getEmail());
        }
        return dto;
    }

}
