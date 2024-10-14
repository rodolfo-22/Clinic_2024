package org.clinica.parcial.controllers;


import org.clinica.parcial.domain.dtos.AcceptAppointmentDTO;
import org.clinica.parcial.domain.dtos.DoctorDTO;
import org.clinica.parcial.domain.dtos.MedicalAppointmentDTO;
import org.clinica.parcial.domain.dtos.res.GeneralResponse;
import org.clinica.parcial.domain.dtos.RequestMedicalAppointmentDTO;
import org.clinica.parcial.domain.entities.*;
import org.clinica.parcial.services.RoleService;
import org.clinica.parcial.services.SpecialAppointmentService;

import org.clinica.parcial.services.SpecialityService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import org.clinica.parcial.services.UserService;
import org.clinica.parcial.services.AppointmentService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.*;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final UserService userService;
    private final SpecialityService specialityService;
    private final RoleService roleService;

    private final SpecialAppointmentService specialAppointmentService;

    public AppointmentController(AppointmentService appointmentService,
                                 UserService userService,
                                 SpecialityService specialityService,
                                 RoleService roleService,
                                 SpecialAppointmentService specialAppointmentService ) {
        this.appointmentService = appointmentService;
        this.userService = userService;
         this.specialityService = specialityService;
         this.roleService = roleService;
        // Error creating bean with name 'appointmentController' defined in file AppointmentController.class]: Unsatisfied dependency expressed through constructor parameter 3: Error creating bean with name 'specialAppointmentServiceImpl' defined in file
          this.specialAppointmentService = specialAppointmentService;
    }

    @PostMapping("/request")
    public ResponseEntity<GeneralResponse> createAppointment(@RequestBody @Valid RequestMedicalAppointmentDTO appointmentDTO) {
        try {
            User user = userService.findUserAuthenticated();
            if (user == null) {
                return GeneralResponse.builder()
                        .status(HttpStatus.UNAUTHORIZED)
                        .message("User not found")
                        .getResponse();
            }

            MedicalAppointmentDTO appointment = appointmentService.createAppointment(appointmentDTO, user);
            return GeneralResponse.builder()
                    .status(HttpStatus.CREATED)
                    .message("Appointment created successfully")
                    .data(appointment)
                    .getResponse();
        } catch (Exception e) {
            return GeneralResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message("An error occurred while creating the appointment")
                    .getResponse();
        }
    }


    @PostMapping("/approve")
    @PreAuthorize("hasAuthority('Asistente')")
    public ResponseEntity<GeneralResponse> approveAppointment(@RequestBody @Valid AcceptAppointmentDTO appointmentDTO) {
        try {
            MedicalAppointment appointment = appointmentService.findById(appointmentDTO.getAppointmentId());
            if (appointment == null) {
                return GeneralResponse.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .message("Appointment not found")
                        .getResponse();
            }

            if (!appointmentDTO.getIsAccepted()) {
                appointment.setState("denied");
                appointmentService.saveAppointment(appointment);
                return GeneralResponse.builder()
                        .status(HttpStatus.OK)
                        .message("Appointment denied")
                        .getResponse();
            }

            Date estimatedEndTime = new Date(appointmentDTO.getFechaRealizacion().getTime() + appointmentDTO.getDuration() * 60000L);
            appointment.setEstimadaFinalizacionDate(estimatedEndTime);
            appointment.setRealizacionDate(appointmentDTO.getFechaRealizacion());
            appointment.setState("approved");
            appointmentService.saveAppointment(appointment);

            for (UUID doctorId : appointmentDTO.getDoctorsId()) {
                User doctor = userService.findById(doctorId);
                if (doctor == null || doctor.getRoles().stream().noneMatch(role -> role.getName().equals("Doctor"))) {
                    return GeneralResponse.builder()
                            .status(HttpStatus.BAD_REQUEST)
                            .message("One or more provided doctors are invalid or do not have the 'Doctor' role")
                            .getResponse();
                }

                for (String specialtyName : appointmentDTO.getSpecialties()) {
                    Optional<Speciality> specialty = specialityService.findByName(specialtyName);
                    if (specialty.isEmpty()) {
                        return GeneralResponse.builder()
                                .status(HttpStatus.NOT_FOUND)
                                .message("Speciality not found")
                                .getResponse();
                    } else {
                        SpecialAppointment specialAppointment = new SpecialAppointment();
                        specialAppointment.setUser(doctor);
                        specialAppointment.setSpecialty(specialty.get());
                        specialAppointment.setAppointment(appointment);
                        specialAppointmentService.save(specialAppointment);
                    }
                }
            }

            return GeneralResponse.builder()
                    .status(HttpStatus.OK)
                    .message("Appointment approved")
                    .getResponse();
        } catch (Exception e) {
            return GeneralResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message("An error occurred while approving the appointment")
                    .getResponse();
        }
    }

    @GetMapping("/own")
    public ResponseEntity<GeneralResponse> getAppointmentsByUser(@RequestParam(value = "state", required = false) String state) {
        try {
            User user = userService.findUserAuthenticated();
            if (user == null) {
                return GeneralResponse.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .message("User not found")
                        .getResponse();
            }

            List<MedicalAppointmentDTO> appointments;
            if (state != null && !state.isEmpty()) {
                appointments = appointmentService.getAppointmentsByUserAndState(user, state);
            } else {
                appointments = appointmentService.getAppointmentsByUser(user);
            }

            return GeneralResponse.builder()
                    .status(HttpStatus.OK)
                    .message("Appointments found successfully")
                    .data(appointments)
                    .getResponse();
        } catch (Exception e) {
            return GeneralResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message("An error occurred while fetching the appointments")
                    .getResponse();
        }
    }

    @PostMapping("/start/{appointmentId}")
    @PreAuthorize("hasAuthority('Doctor')")
    public ResponseEntity<GeneralResponse> startAppointment(@PathVariable("appointmentId") String appointmentId) {
        try {
            User user = userService.findUserAuthenticated();
            if (user == null) {
                return GeneralResponse.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .message("Usuario no encontrado")
                        .getResponse();
            }

            Role docRole = roleService.findByName("Doctor").orElse(null);
            if (docRole == null) {
                return GeneralResponse.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .message("Rol de doctor no encontrado")
                        .getResponse();
            }
            if (!user.getRoles().contains(docRole)) {
                return GeneralResponse.builder()
                        .status(HttpStatus.UNAUTHORIZED)
                        .message("El usuario no es un doctor")
                        .getResponse();
            }

            MedicalAppointment appointment = appointmentService.findById(UUID.fromString(appointmentId));
            if (appointment == null) {
                return GeneralResponse.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .message("Cita no encontrada")
                        .getResponse();
            }

            appointment.setRealizacionDate(new Date());
            appointment.setState("in-progress");
            appointmentService.saveAppointment(appointment);

            return GeneralResponse.builder()
                    .status(HttpStatus.OK)
                    .message("Cita iniciada con éxito")
                    .data(appointment)
                    .getResponse();

        } catch (Exception e) {
            return GeneralResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message("Ocurrió un error al iniciar la cita")
                    .getResponse();
        }
    }

    @PostMapping("/finish/{appointmentId}")
    @PreAuthorize("hasAuthority('Doctor')")
    public ResponseEntity<GeneralResponse> appointmentFinish(@PathVariable("appointmentId") String appointmentId) {
        try {
            User user = userService.findUserAuthenticated();
            if (user == null) {
                return GeneralResponse.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .message("Usuario no encontrado")
                        .getResponse();
            }

            Role docRole = roleService.findByName("Doctor").orElse(null);
            if (docRole == null) {
                return GeneralResponse.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .message("Rol de doctor no encontrado")
                        .getResponse();
            }
            if (!user.getRoles().contains(docRole)) {
                return GeneralResponse.builder()
                        .status(HttpStatus.UNAUTHORIZED)
                        .message("El usuario no es un doctor")
                        .getResponse();
            }

            MedicalAppointment appointment = appointmentService.findById(UUID.fromString(appointmentId));
            if (appointment == null) {
                return GeneralResponse.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .message("Cita no encontrada")
                        .getResponse();
            }

            Date now = new Date();
            long duration = (now.getTime() - appointment.getRealizacionDate().getTime()) / (1000 * 60); // Duración en minutos
            appointment.setFinalizationDate(now);
            appointment.setDuration((int) duration);
            appointment.setState("finished");
            appointmentService.saveAppointment(appointment);

            return GeneralResponse.builder()
                    .status(HttpStatus.OK)
                    .message("Cita finalizada con éxito")
                    .data(appointment)
                    .getResponse();

        } catch (Exception e) {
            return GeneralResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message("Ocurrió un error al finalizar la cita")
                    .getResponse();
        }
    }

    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<GeneralResponse> cancelAppointment(@PathVariable UUID id) {
        try {
            appointmentService.cancelAppointment(id);
            return GeneralResponse.builder()
                    .status(HttpStatus.OK)
                    .message("Appointment cancelled successfully")
                    .getResponse();
        } catch (Exception e) {
            return GeneralResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message("An error occurred while cancelling the appointment")
                    .getResponse();
        }
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('Asistente')")
    public ResponseEntity<GeneralResponse> getAllAppointments() {
        try {
            List<MedicalAppointment> appointments = appointmentService.findAllAppointments();
            return GeneralResponse.builder()
                    .status(HttpStatus.OK)
                    .message("Appointments retrieved")
                    .data(appointments)
                    .getResponse();
        } catch (Exception e) {
            e.printStackTrace();
            return GeneralResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message("An error occurred while retrieving appointments")
                    .getResponse();
        }
    }

    @GetMapping("/available-doctors")
    @PreAuthorize("hasAuthority('Asistente')")
    public ResponseEntity<GeneralResponse> getAvailableDoctors(
            @RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date startTime,
            @RequestParam("duration") int duration) {
        try {
            List<User> doctors = userService.findAvailableDoctors(startTime, duration);
            return GeneralResponse.builder()
                    .status(HttpStatus.OK)
                    .message("Available doctors retrieved")
                    .data(doctors)
                    .getResponse();
        } catch (Exception e) {
            e.printStackTrace();
            return GeneralResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message("An error occurred while retrieving available doctors")
                    .getResponse();
        }
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<GeneralResponse> handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        return GeneralResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Invalid date format. Please use the format: yyyy-MM-dd'T'HH:mm:ss")
                .getResponse();
    }



}
