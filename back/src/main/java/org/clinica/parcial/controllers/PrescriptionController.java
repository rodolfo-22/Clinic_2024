package org.clinica.parcial.controllers;

import org.clinica.parcial.domain.dtos.MedicalAppointmentDTO;
import org.clinica.parcial.domain.dtos.res.*;
import org.clinica.parcial.domain.dtos.ManyPrescriptionRequest;
import org.clinica.parcial.domain.entities.*;
import org.clinica.parcial.services.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clinic")
public class PrescriptionController {
    final UserService userService;
    final RoleService roleService;

    final AppointmentService appointmentService;

    final PrescriptionService prescriptionService;
    final HistoryService historyService;
    final SpecialityService specialityService;

    public PrescriptionController(UserService userService, RoleService roleService, AppointmentService appointmentService, PrescriptionService prescriptionService, HistoryService historyService, SpecialityService specialityService) {
        this.userService = userService;
        this.roleService = roleService;
        this.appointmentService = appointmentService;
        this.prescriptionService = prescriptionService;
        this.historyService = historyService;
        this.specialityService = specialityService;
    }

    @PostMapping("/prescription/{appointmentId}")
    @PreAuthorize("hasAuthority('Doctor')")
    public ResponseEntity<GeneralResponse> createPrescription(@PathVariable("appointmentId") String appointmentId, @RequestBody ManyPrescriptionRequest req, BindingResult errors) {
        try {
            if(errors.hasErrors()){
                return GeneralResponse.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .message("Invalid request")
                        .data(errors.getAllErrors())
                        .getResponse();
            }
            User user = userService.findUserAuthenticated();
            if (user == null) {
                return GeneralResponse.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .message("User not found")
                        .getResponse();
            }

            Role docRole = roleService.findByName("Doctor").orElse(null);
            if (docRole == null) {
                return GeneralResponse.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .message("Role not found")
                        .getResponse();
            }
            if (!user.getRoles().contains(docRole)) {
                return GeneralResponse.builder()
                        .status(HttpStatus.UNAUTHORIZED)
                        .message("User is not a doctor")
                        .getResponse();
            }

            MedicalAppointment appointment = appointmentService.findById(UUID.fromString(appointmentId));
            if (appointment == null) {
                return GeneralResponse.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .message("Appointment not found")
                        .getResponse();
            }

            prescriptionService.createPrescription(appointment, req);

            return GeneralResponse.builder()
                    .status(HttpStatus.OK)
                    .message("Prescriptions added successfully")
                    .data(appointment)
                    .getResponse();

        } catch (Exception e) {
            return GeneralResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message("An error occurred while adding the prescriptions")
                    .getResponse();
        }
    }

    @GetMapping("/prescription/{userId}")
    public ResponseEntity<GeneralResponse> getUserPrescriptions(@PathVariable("userId") UUID userId) {
        try {
            // Getting user authenticated
            User user = userService.findUserAuthenticated();
            if (user == null) {
                return GeneralResponse.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .message("User not found")
                        .getResponse();
            }

            Role docRole = roleService.findByName("Doctor").orElse(null);
            if (docRole == null) {
                return GeneralResponse.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .message("Role not found")
                        .getResponse();
            }
            if (!user.getRoles().contains(docRole)) {
                return GeneralResponse.builder()
                        .status(HttpStatus.UNAUTHORIZED)
                        .message("User is not a doctor")
                        .getResponse();
            }

            List<PrescriptionDTO> prescriptionList = prescriptionService.findPrescriptionByUser(userId);

            return GeneralResponse.builder()
                    .status(HttpStatus.OK)
                    .message("Prescriptions found successfully")
                    .data(prescriptionList)
                    .getResponse();

        } catch (Exception e) {
            return GeneralResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message("An error occurred while fetching the prescriptions")
                    .getResponse();
        }
    }

    @GetMapping("/prescriptions")
    public ResponseEntity<GeneralResponse> getUserPrescriptions() {
        try {
            // Getting user authenticated
            User user = userService.findUserAuthenticated();
            if (user == null) {
                return GeneralResponse.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .message("User not found")
                        .getResponse();
            }

            List<Prescription> prescriptionList = prescriptionService.findPrescriptionsForAuthenticatedUser(user);

            return GeneralResponse.builder()
                    .status(HttpStatus.OK)
                    .message("Prescriptions found successfully")
                    .data(prescriptionList)
                    .getResponse();

        } catch (Exception e) {
            return GeneralResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message("An error occurred while fetching the prescriptions")
                    .getResponse();
        }
    }

    @GetMapping("/schedule")
    @PreAuthorize("hasAuthority('Doctor')")
    public ResponseEntity<GeneralResponse> getDoctorAppointments(@RequestParam("date") String date) {
        try {
            User user = userService.findUserAuthenticated();
            if (user == null) {
                return GeneralResponse.builder()
                        .status(HttpStatus.UNAUTHORIZED)
                        .message("User not found")
                        .getResponse();
            }
            SimpleDateFormat annotation = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedDate = annotation.parse(date);
            List<MedicalAppointment> appointments = appointmentService.getAppointmentsByDoctorAndDate(user, parsedDate);
            List<MedicalAppointmentDTO> appointmentDTOs = appointments.stream()
                    .map(appointmentService::toDTO)
                    .collect(Collectors.toList());

            return GeneralResponse.builder()
                    .status(HttpStatus.OK)
                    .message("Appointments retrieved successfully")
                    .data(appointmentDTOs)
                    .getResponse();
        } catch (Exception e) {
            e.printStackTrace(); // Log de excepción
            return GeneralResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message("Internal server error!")
                    .getResponse();
        }
    }
}


