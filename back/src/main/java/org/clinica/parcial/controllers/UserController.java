package org.clinica.parcial.controllers;

import jakarta.validation.Valid;
import org.clinica.parcial.domain.dtos.DoctorDTO;
import org.clinica.parcial.domain.dtos.UserDTO;
import org.clinica.parcial.domain.dtos.res.GeneralResponse;
import org.clinica.parcial.domain.dtos.HistoryReqDTO;
import org.clinica.parcial.domain.dtos.RoleDTO;
import org.clinica.parcial.domain.dtos.res.HistoryResDTO;
import org.clinica.parcial.domain.entities.History;
import org.clinica.parcial.domain.entities.Role;
import org.clinica.parcial.domain.entities.User;
import org.clinica.parcial.services.HistoryService;
import org.clinica.parcial.services.RoleService;
import org.clinica.parcial.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    final UserService userService;
    final RoleService roleService;

    @Autowired
    HistoryService historyService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/")
    public ResponseEntity<GeneralResponse> findAllUser() {
        try {
            List<User> users = userService.findAllUser();
            List<UserDTO> userDTOs = users.stream()
                    .map(user -> new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRoles()))
                    .collect(Collectors.toList());
            return GeneralResponse.builder()
                    .status(HttpStatus.OK)
                    .message("Found all users")
                    .data(userDTOs)
                    .getResponse();
        } catch (Exception e) {
            return GeneralResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).getResponse();
        }
    }


    @GetMapping("/{_id}")
    public ResponseEntity<GeneralResponse> findUserById(@PathVariable("_id") String id){
        try{
            return GeneralResponse.builder().status(HttpStatus.OK).message("found").getResponse();

        }
        catch (Exception e){
            return GeneralResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).getResponse();
        }
    }

    @PostMapping("/{_id}")
    public ResponseEntity<GeneralResponse> updateUserById(@PathVariable("_id") String id){
        try{
            return GeneralResponse.builder().status(HttpStatus.OK).message("updated").getResponse();

        }
        catch (Exception e){
            return GeneralResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).getResponse();
        }
    }

    @DeleteMapping("/{_id}")
    public ResponseEntity<GeneralResponse> deleteUserById(@PathVariable("_id") String id){
        try{
            return GeneralResponse.builder().status(HttpStatus.OK).message("deleted").getResponse();

        }
        catch (Exception e){
            return GeneralResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).getResponse();
        }
    }


    @PutMapping("/roles/{userId}")
    public ResponseEntity<GeneralResponse> addRoleToUser(@PathVariable UUID userId, @RequestBody @Valid RoleDTO info) {
        try {
            User user = userService.findOneById(userId);
            if (user == null) {
                return GeneralResponse.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .message("User not found")
                        .getResponse();
            }

            Optional<Role> findRole = roleService.findByName(info.getRole());

            if (findRole.isEmpty()) {
                return GeneralResponse.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .message("Role not found")
                        .getResponse();
            }

            Role roleToAdd = findRole.get(); // Obtener el objeto Role del Optional

            // Verificar si el usuario ya tiene el rol
            if (user.getRoles().contains(roleToAdd)) {
                return GeneralResponse.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .message("User already has the specified role")
                        .getResponse();
            }

            user.getRoles().add(roleToAdd);
            userService.saveUser(user);

            return GeneralResponse.builder()
                    .status(HttpStatus.OK)
                    .message("Role added to user successfully")
                    .data(user)
                    .getResponse();

        } catch (Exception e) {
            System.out.println("E"+e);
            return GeneralResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message("An error occurred while adding role to user")
                    .getResponse();

        }
    }

    @DeleteMapping("/roles/{userId}")
    public ResponseEntity<GeneralResponse> clearRolesFromUser(@PathVariable UUID userId) {
        try {
            User user = userService.findOneById(userId);
            if (user == null) {
                return GeneralResponse.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .message("User not found")
                        .getResponse();
            }


            user.getRoles().clear();

            userService.saveUser(user);


            return GeneralResponse.builder()
                    .status(HttpStatus.OK)
                    .message("Roles cleared from user successfully")
                    .data(user)
                    .getResponse();
        } catch (Exception e) {
            return GeneralResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message("An error occurred while clearing roles from user")
                    .getResponse();
        }
    }

    @PostMapping("/record/add")
    @PreAuthorize("hasAnyAuthority('Doctor', 'Asistente')")
    public ResponseEntity<GeneralResponse> createUserHistory(@RequestBody HistoryReqDTO historyReqDTO){
        try{
            User user = userService.findOneByIdentifier(historyReqDTO.getIdentifier());
            if(user == null){
                return GeneralResponse.builder().status(HttpStatus.NOT_FOUND).message("User not found").getResponse();
            }
            History newHistory = new History();
            newHistory.setUser(user);
            newHistory.setTimestamp(Date.from(Instant.now()));
            newHistory.setReason(historyReqDTO.getReason());
            historyService.createHistory(newHistory);

            return GeneralResponse.builder().status(HttpStatus.OK).message("History Saved!").getResponse();

        } catch (Exception e){
            return GeneralResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).getResponse();
        }
    }

    @GetMapping("/record/list")
    @PreAuthorize("hasAuthority('Patient')")
    public ResponseEntity<GeneralResponse> getUserMedicalHistory(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        try {
            User user = userService.findUserAuthenticated();
            if (user == null) {
                return GeneralResponse.builder().status(HttpStatus.NOT_FOUND).message("User not found").getResponse();
            }

            List<History> historyList = historyService.findHistoryByUserAndDateRange(user, startDate, endDate);
            List<HistoryResDTO> historyDTOs = historyList.stream()
                    .map(history -> new HistoryResDTO(history.getId(), history.getTimestamp(), history.getReason()))
                    .collect(Collectors.toList());

            return GeneralResponse.builder()
                    .status(HttpStatus.OK)
                    .message("History retrieved successfully")
                    .data(historyDTOs)
                    .getResponse();
        } catch (Exception e) {
            return GeneralResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message("An error occurred while retrieving the history").getResponse();
        }
    }

    {/*    @GetMapping("/doctors")
    public ResponseEntity<GeneralResponse> getAllDoctors() {
        try {
            Role doctorRole = roleService.findByName("Doctor").orElse(null);
            if (doctorRole == null) {
                return GeneralResponse.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .message("Doctor role not found")
                        .getResponse();
            }

            Date currentTime = new Date(); // Suponiendo que estás chequeando la disponibilidad para citas actuales

            List<User> doctors = userService.findAllUsersByRole(doctorRole);
            List<DoctorDTO> doctorDTOs = doctors.stream()
                    .map(doctor -> new DoctorDTO(doctor.getId(), doctor.getUsername(), doctor.isAvailable(currentTime, 0))) // Ajusta los parámetros según sea necesario
                    .collect(Collectors.toList());

            return GeneralResponse.builder()
                    .status(HttpStatus.OK)
                    .message("Doctors retrieved")
                    .data(doctorDTOs)
                    .getResponse();
        } catch (Exception e) {
            return GeneralResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message("An error occurred while retrieving doctors")
                    .getResponse();
        }
    }*/}

}
