package org.clinica.parcial.controllers;

import org.clinica.parcial.domain.dtos.res.GeneralResponse;
import org.clinica.parcial.domain.dtos.res.HistoryResDTO;
import org.clinica.parcial.domain.entities.History;
import org.clinica.parcial.domain.entities.User;
import org.clinica.parcial.services.HistoryService;
import org.clinica.parcial.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/patient")
public class PatientController {
    private final UserService userService;
    private final HistoryService historyService;

    public PatientController(UserService userService, HistoryService historyService) {
        this.userService = userService;
        this.historyService = historyService;
    }

    @GetMapping("/record/list")
    @PreAuthorize("hasAuthority('Patient')")
    public ResponseEntity<GeneralResponse> getAuthenticatedUserMedicalHistory() {
        try {
            User user = userService.findUserAuthenticated();
            if (user == null) {
                return GeneralResponse.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .message("User not found")
                        .getResponse();
            }

            List<History> historyList = historyService.findHistoryByUser(user);
            List<HistoryResDTO> historyDTOs = historyList.stream()
                    .map(history -> new HistoryResDTO(history.getId(), history.getTimestamp(), history.getReason()))
                    .collect(Collectors.toList());

            return GeneralResponse.builder()
                    .status(HttpStatus.OK)
                    .message("History retrieved successfully")
                    .data(historyDTOs)
                    .getResponse();
        } catch (Exception e) {
            return GeneralResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message("An error occurred while retrieving the history")
                    .getResponse();
        }
    }
}
